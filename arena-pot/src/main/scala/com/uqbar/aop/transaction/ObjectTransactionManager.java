package com.uqbar.aop.transaction;

import java.util.HashMap;
import java.util.Map;

import com.uqbar.aop.transaction.utils.BasicTaskOwner;
import com.uqbar.common.transaction.Context;
import com.uqbar.common.transaction.ObjectTransaction;
import com.uqbar.common.transaction.TaskOwner;
import com.uqbar.commons.exceptions.ProgramException;

/**
 * Manages the life-cycle of all {@link ObjectTransaction} objects.
 * DOCME
 * 
 * @author nny
 * @author jfernandes
 * @author npasserini
 *
 */
public class ObjectTransactionManager {
    private static final String OBJECT_TRANSACTOR_CONTEXT_KEY = "object.transactor.context.key";
    private static final String TRANSACTION_REGISTR_KEY = "object.transactor.registry.key";
    private static long transactionId = 0;
    
    static{
    	beginNullTransaction(new BasicTaskOwner("null-transacion"));
    }

    // ***************************************
    // ** Basic Transaction LifeCycle Methods
    // ***************************************

    /**
     * Starts a transaction.
     * If there's already another one in progress then the new one will be a child for that. 
     */
    public static void begin(TaskOwner owner) {
    	ObjectTransactionImpl transaction = (ObjectTransactionImpl) getTransaction();
    	transaction = owner.isTransactional() ? createObjectTransaction(owner, transaction) : createObjectNullTransaction(owner, transaction);
    	transaction.logger().debug("Starting Transaction id=[" + transaction.getId() + "]...");
    	setTransaction(transaction);
    	registerTransaction(transaction);
    	transaction.setState(ObjectTransactionImpl.STATE_STARTED());
    	transaction.logger().debug("Transaction STARTED id=[" + transaction.getId() + "] !");
    }

    /**
     * Finalize the current transaction applying all the changes.
     * @see ObjectTransactionImpl#commit()
     */
    public static void commit(TaskOwner owner) {
    	assertUnderTransaction();
    	assertOwnership(owner);
    	
    	ObjectTransactionImpl transaction = (ObjectTransactionImpl) getTransaction();
    	transaction.logger().debug("Performing COMMIT on transaction id=[" + transaction.getId() + "]...");
    	setTransaction(transaction.parent());
		transaction.commit();
    	unregisterTransaction(transaction);
    	transaction.logger().debug("Transaction COMMITED id=[" + transaction.getId() + "] next transaction is id=[" + transaction.getId() + "]");
    }

    /**
     * Finalize the current transaction without applying its changes
     */
    public static void rollback(TaskOwner owner) {
    	assertUnderTransaction();
    	assertOwnership(owner);
    	
    	ObjectTransactionImpl transaction = (ObjectTransactionImpl) getTransaction();
    	transaction.logger().debug("Performing ROLLBACK on transaction id=[" + transaction.getId() + "]...");
    	
    	ObjectTransaction nextTransaction = transaction.rollback();
    	unregisterTransaction(getTransaction());
    	setTransaction(nextTransaction);
    	
    	transaction.logger().debug("Transaction ROLLBACKED id=[" + transaction.getId() + "] next transaction is id=[" + transaction.getId() + "]");
    }

    /**
     * REFACTORME rollbackea la transaccion del owner
     * @param owner
     */
    public static void rollbackChild(TaskOwner owner) {
    	//assertUnderTransaction();
    	ProgramException.assertNotNull(owner.getTransaction(), "The TaskOwner doesn't have an associated transaction!");
    	//assertOwnership(owner);
    	((ObjectTransactionImpl) owner.getTransaction()).rollback();
    	unregisterTransaction(owner.getTransaction());
    	//setTransaction(objectTransaction);
    }

    // ***************************
    // ** MainUseCase only
    // ***************************
    
    /**
     * Begins a new NullTransaction. This should only be used for the case where no changes are going to be made
     * to objects. Actually for the first main use case. 
     * @throws IllegalStateException if another transaction is already on the context.
     */
    public static void beginNullTransaction(TaskOwner owner) {
    	assertNotUnderTransaction();
    	ObjectTransactionImpl transaction = createObjectNullTransaction(owner, null);
    	setTransaction(transaction);
    	transaction.logger().debug("Starting " + transaction.getClass().getSimpleName() + " id=[" + transaction.getId() + "]");
		registerTransaction(transaction);
    }

    // ***************************
    // ** Transaction Creation
    // ***************************
    
    /**
     * Creates a new transaction but without putting it into the current execution context.
     */
    public static void createSingleTransaction(TaskOwner owner) {
    	ObjectTransactionImpl transaction = owner.isTransactional() ? createObjectTransaction(owner, null) : createObjectNullTransaction(owner, null);
    	registerTransaction(transaction);
    	transaction.setState("Created Single");
    }

    /**
     * Crea una transaccion hija del contexto y no la mete en contexto.
     */
    public static ObjectTransaction createTransaction(TaskOwner owner) {
    	ObjectTransactionImpl transaction = owner.isTransactional() ? 
    		createObjectTransaction(owner, (ObjectTransactionImpl) getTransaction())
    		:
    		createObjectNullTransaction(owner, (ObjectTransactionImpl) getTransaction());
    	registerTransaction(transaction);
    	transaction.setState("Created");
    	return transaction;
    }

    // ******************************************************************************************
    // ** Metodos suspend(), resume() y amiguitos. Para utilizacion solo del Filter de servlets que
    // ** que se encarga de iniciar y terminar el contexto. NO es para uso dentro del framework.
    // ** La transaccion del caso de uso en curso se suspende y vuelve a resumir entre cada pedido
    // ******************************************************************************************
    
    /**
     * Suspende la transaction en curso, seteando null en el contexto.
     */
    public static ObjectTransaction suspend() {
    	assertUnderTransaction();
    	ObjectTransactionImpl transaction = (ObjectTransactionImpl) getTransaction();
    	setTransaction(null);
    	if (transaction != null) {
    		transaction.logger()
    			.debug("Suspending " + transaction.getClass().getSimpleName() + " id=[" + transaction.getId() + "]");
    	}
    	return transaction;
    }

    /**
     * Reanuda la transaccion indicada, poniendola en el contexto.
     * 
     * @param transaction la transaccion a reanudar.
     */
    public static void resume(ObjectTransaction transaction) {
    	assertNotUnderTransaction();
    	ObjectTransactionImpl transactionImpl = (ObjectTransactionImpl) transaction;
    	setTransaction(transaction);
    	transactionImpl.logger()
    		.debug("Resuming " + transaction.getClass().getSimpleName() + "id=[" + transactionImpl.getId() + "]");
    }

    /**
     * Setea una registry de trasaccion donde se van a registrar todas las transaciones que se crean. Para uso
     * desde el filter que inicializa el contexto.
     * 
     * @param transactionMap el mapa de transacciones correspondiente al caso de uso en curso.
     */
    public static void setTransactionRegistry(Map<Long, ObjectTransaction> transactionMap) {
    	Context.getCurrentContext().setParameter(TRANSACTION_REGISTR_KEY, transactionMap);
    }

    // *****************************************************************************
    // ** Transaction accessors - REVISARME aparentemente todo el mundo pide la Tx.
    // *****************************************************************************
    
    /**
     * Retrieves the {@link ObjectTransaction} from the current context.
     * @return ObjectTransaction
     */
    public static ObjectTransaction getTransaction() {
    	return Context.getCurrentContext().getParameter(OBJECT_TRANSACTOR_CONTEXT_KEY);
    }

    // for TransactionMonitor only
    public static Map<Long, ObjectTransaction> getTransactionRegistry() {
    	return Context.getCurrentContext().getParameter(TRANSACTION_REGISTR_KEY);
    }

    // ****************************************
    // ** Transaction Creation Private helpers
    // ****************************************
    // creation
    
    protected static ObjectTransactionImpl createObjectTransaction(TaskOwner owner, ObjectTransactionImpl objectTransactor) {
    	ObjectTransactionImpl transaction = new ObjectTransactionImpl(objectTransactor, owner, transactionId++);
    	owner.setTransaction(transaction);
    	return transaction;
    }

    protected static ObjectTransactionImpl createObjectNullTransaction(TaskOwner owner, ObjectTransactionImpl parentTransaction) {
    	NullObjectTransaction transaction = new NullObjectTransaction(parentTransaction, owner, transactionId++);
    	owner.setTransaction(transaction);
    	return transaction;
    }

    protected static void setTransaction(ObjectTransaction transaction) {
    	Context.getCurrentContext().setParameter(OBJECT_TRANSACTOR_CONTEXT_KEY, transaction);
    }

    // registration
    protected static void registerTransaction(ObjectTransaction transaction) {
    	if (getTransactionRegistry() == null) {
    		setTransactionRegistry(new HashMap<Long, ObjectTransaction>());
    	}
    	Map<Long, ObjectTransaction> transactionMap = getTransactionRegistry();
    	transactionMap.put(transaction.getId(), transaction);
    }

    protected static void unregisterTransaction(ObjectTransaction transaction) {
    	Map<Long, ObjectTransaction> transactionMap = getTransactionRegistry();
    	if (transactionMap != null) {
    		transactionMap.remove(transaction.getId());
    	}
    }

    // ******************************
    // ** Assertions private helpers
    // ******************************
    
    protected static void assertUnderTransaction() {
    	if (getTransaction() == null) {
    		throw new IllegalStateException("No object transaction in this context!!");
    	}
    }

    protected static void assertOwnership(TaskOwner owner) {
    	if (!(getTransaction().getOwner() == owner)) {
    		throw new IllegalStateException("The object: " + owner + ", doesn't have permission over the current transaction.");
    	}
    }

    protected static void assertNotUnderTransaction() {
    	if (getTransaction() != null) {
    		throw new IllegalStateException("An object transaction already exists for the current context!!");
    	}
    }

    /**
     * Replicates all the state this transaction currently has for the given "sourceObject" into the given "targetObject".
     * This relies on the fact that the "clone" methods of your objects needs to call this method in order to copy
     * the real state of the object (being stored in this transaction instead of the object itself).
     * @param sourceObject source object to be cloned
     * @param targetObject target object, the "clone"
     */
    public static void cloneAttributes(Object sourceObject, Object targetObject) {
    	ObjectTransaction transaction = getTransaction();
		if (transaction != null) {
    		((ObjectTransactionImpl) transaction).cloneAttributes(sourceObject, targetObject);
    	}
    }
    
}