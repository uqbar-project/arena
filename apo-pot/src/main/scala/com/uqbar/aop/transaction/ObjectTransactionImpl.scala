package com.uqbar.aop.transaction;

import java.lang.reflect.Modifier

import scala.collection.JavaConversions.asScalaBuffer
import scala.collection.JavaConversions.bufferAsJavaList
import scala.collection.JavaConversions.mutableMapAsJavaMap
import scala.collection.mutable.Buffer
import scala.collection.mutable.Map

import org.apache.commons.logging.LogFactory
import org.uqbar.commons.utils.Observable
import org.uqbar.commons.utils.ReflectionUtils
import org.uqbar.commons.utils.Transactional

import com.uqbar.common.transaction.Collection.TransacionalList
import com.uqbar.common.transaction.Collection.TransactionalData
import com.uqbar.common.transaction.Collection.TransactionalMap
import com.uqbar.common.transaction.Collection.TransactionalSet
import com.uqbar.common.transaction.ObjectTransaction
import com.uqbar.common.transaction.TaskOwner
import com.uqbar.commons.exceptions.ProgramException

/**
 * {@link ObjectTransaction} default implementation.
 * Stores all the state specific for this transaction. That means all the field changed while executing
 * the domain logic with this transaction as part of the current context.
 *
 * Weaving the domain object allows us to redirect all the field reading and writting behavior
 * to be redirected to this class, instead of directly accessing the state of the object.
 *
 * This allow us to have isolation for each transaction in terms of object's state.
 *
 * @author jfernandes
 * @author npasserini
 * @author nny
 */

object ObjectTransactionImpl {
  val STATE_ROLLBACKED = "ROLLBACKED"
  val STATE_COMMITED = "COMMITED"
  val STATE_STARTED = "STARTED"
  val STATE_NEW = "NEW"
  val LOG = LogFactory.getLog(classOf[ObjectTransaction])
}

class ObjectTransactionImpl(var parent: ObjectTransactionImpl, var owner: TaskOwner, var id: Long) extends ObjectTransaction {
  /** Holds the state (fields) for all objects belonging to (and modified during) this transaction  */
  // TODO: use a java.util.IdentityHashMap instance instead of this
  val attributeMap = Map[IdentityWrapper, Map[String, Any]]()
  val children = Buffer[ObjectTransactionImpl]()
  var state = ObjectTransactionImpl.STATE_NEW
  this.addParentChild()

  // ******************************
  // ** Simple Fields Interception
  // ** each weaved object will call this methods every time the execution flow changes one of
  // ** their fields.
  // ******************************

  def fieldWrite(owner: Object, fieldName: String, newValue: java.util.List[_], oldValue: java.util.List[_]): java.util.List[_] = {
    this.doFieldWrite(owner, fieldName, if (newValue == null) newValue else new TransacionalList(newValue, owner, fieldName), oldValue)
  }

  def fieldWrite(owner: Object, fieldName: String, newValue: java.util.Map[_, _], oldValue: java.util.Map[_, _]): java.util.Map[_, _] = {
    this.doFieldWrite(owner, fieldName, if (newValue == null) newValue else new TransactionalMap(newValue, owner, fieldName), oldValue)
  }

  def fieldWrite(owner: Object, fieldName: String, newValue: java.util.Set[_], oldValue: java.util.Set[_]): java.util.Set[_] = {
    this.doFieldWrite(owner, fieldName, if (newValue == null) newValue else new TransactionalSet(newValue, owner, fieldName), oldValue)
  }

  def fieldWrite(owner: Object, fieldName: String, newValue: Int, oldValue: Int): Int = this.doFieldWrite(owner, fieldName, newValue, oldValue);
  def fieldWrite(owner: Object, fieldName: String, newValue: Float, oldValue: Float): Float = this.doFieldWrite(owner, fieldName, newValue, oldValue);
  def fieldWrite(owner: Object, fieldName: String, newValue: Long, oldValue: Long): Long = this.doFieldWrite(owner, fieldName, newValue, oldValue);
  def fieldWrite(owner: Object, fieldName: String, newValue: Double, oldValue: Double): Double = this.doFieldWrite(owner, fieldName, newValue, oldValue);
  def fieldWrite(owner: Object, fieldName: String, newValue: Short, oldValue: Short): Short = this.doFieldWrite(owner, fieldName, newValue, oldValue);
  def fieldWrite(owner: Object, fieldName: String, newValue: Byte, oldValue: Byte): Byte = this.doFieldWrite(owner, fieldName, newValue, oldValue);
  def fieldWrite(owner: Object, fieldName: String, newValue: Object, oldValue: Object): Object = this.doFieldWrite(owner, fieldName, newValue, oldValue);

  /**
   * Stores the new value in the transaction's map associated to the target object.
   * If the object is not in the map already, then it'll put it the first time.
   */
  def doFieldWrite[T](owner: Object, fieldName: String, newValue: T, oldValue: T): T = {
    this.getValueMap(IdentityWrapper.wrapKey(owner)).put(fieldName, newValue)
    return oldValue;
  }

  def fieldRead(owner: Any, fieldName: String, value: Long): Long = doFieldRead(owner, fieldName, value)
  def fieldRead(owner: Any, fieldName: String, value: Short): Short = doFieldRead(owner, fieldName, value)
  def fieldRead(owner: Any, fieldName: String, value: Double): Double = doFieldRead(owner, fieldName, value)
  def fieldRead(owner: Any, fieldName: String, value: Float): Float = doFieldRead(owner, fieldName, value)
  def fieldRead(owner: Any, fieldName: String, value: Int): Int = doFieldRead(owner, fieldName, value)
  def fieldRead(owner: Any, fieldName: String, value: Byte): Byte = doFieldRead(owner, fieldName, value)
  def fieldRead(owner: Any, fieldName: String, value: Any): Any = doFieldRead(owner, fieldName, value)

  /**
   * Checks whether the value is in the transaction's map (meaning it was modified during this transaction).
   * If that's the case, then it will return that value.
   * Otherwise, it delegates the value retrieving to its parent transaction (that will do the same).
   */
  def doFieldRead[A](owner: Any, fieldName: String, value: A): A = {
    val valueMap = this.getValueMap(IdentityWrapper.wrapKey(owner), false);
    if (valueMap != null && valueMap.containsKey(fieldName)) {
      return valueMap(fieldName).asInstanceOf[A]
    }
    if (this.parent != null) {
      return this.parent.doFieldRead(owner, fieldName, value)
    }
    return value;
  }

  def setPropertyValuesAfterInit(owner: Object) {
    owner match {
      case o: TransactionalData[_] =>
      case _ => {
        val fields = ReflectionUtils.getAllFields(owner).filter(f => { !(Modifier.isTransient(f.getModifiers()) || Modifier.isFinal(f.getModifiers()) || Modifier.isStatic(f.getModifiers())) })
        fields.foreach(field => {
          field.setAccessible(true);
          val value = field.get(owner)
          val fieldName = field.getName()
          value match {
            case l: TransactionalData[_] =>
            case l: java.util.List[_] => setFieldValue(owner, fieldName, fieldWrite(owner, fieldName, l, l))
            case l: java.util.Set[_] => setFieldValue(owner, fieldName, fieldWrite(owner, fieldName, l, l))
            case l: java.util.Map[_, _] => setFieldValue(owner, fieldName, fieldWrite(owner, fieldName, l, l))
            case _ =>
          }
        })
      }
    }
  }

  def setFieldValue(owner: Object, fieldName: String, value: Object) {
    val field = ReflectionUtils.getField(owner.getClass(), fieldName)
    field.setAccessible(true)
    field.set(owner, fieldWrite(owner, fieldName, value, value))
  }

  // ***************************
  // ** Transaction LifeCycle
  // ***************************

  /**
   * Commits all changes to the parent transaction, if it does exists, or directly to the objects if it doesn't.
   * Also it changes to an "Commited" state.
   * @return the parent transaction or null if it hasn't one.
   */
  protected def commit(): ObjectTransaction = {
    // TODO: use Polymorphism: NullObjectTransaction should be responsable of commiting to objects.
    //    	if (this.parent != null && !(this.parent instanceof NullObjectTransaction)) {
    //    		this.parent.commitMap(this.attributeMap);
    //    	}
    //    	else {
    this.populateValuesToObjects();
    //    	}
    this.removeFromParent();
    logger.debug(s"Commiting Object Transaction id[$id]");
    transitionateToParentTransaction(ObjectTransactionImpl.STATE_COMMITED);
  }

  /**
   * @return
   */
  protected def removeParent(): ObjectTransaction = {
    val transaction = this.parent;
    this.parent = null;
    transaction;
  }

  /**
   * Ends this transaction wasting all changes it might have.
   * @return the parent transaction.
   */
  protected def rollback(): ObjectTransaction = {
    logger.debug(s"Rollbacking Object Transaction id[$id]");
    removeFromParent();
    transitionateToParentTransaction(ObjectTransactionImpl.STATE_ROLLBACKED);
  }

  protected def transitionateToParentTransaction(newState: String): ObjectTransaction = {
    val nextTransaction = this.removeParent();
    logger.debug("Next Transaction: [" + (if (nextTransaction != null) nextTransaction.getId() else null) + "]");
    this.state = newState;
    nextTransaction;
  }

  /**
   * Commits the given changeset received as parameter into this transaction's current state.
   * This is used by a child transaction that is commited and therefore needs to commit all of its changed
   * into the parent transaction.
   */
  protected def commitMap(attributeMap: Map[IdentityWrapper, Map[String, Any]]) {
    attributeMap.foreach { case (k, v) => addValues(k, v) }
  }

  /**
   * Commits all the changes to the real objects
   */
  protected def populateValuesToObjects() {
    this.attributeMap.keySet.foreach { keyWrapper =>
      keyWrapper.synchronized {
        getValueMap(keyWrapper).foreach {
          case (k, v) =>
            try {
              ReflectionUtils.invokeSetter(keyWrapper.getKey(), k, v);
            } catch {
              case e: RuntimeException => throw new ProgramException(e) //
                .addInfo("Object", keyWrapper.getKey()) //
                .addInfo("Field", k) //
                .addInfo("Value", v);
            }
        }
      }

    }
  }

  // ***************************
  // ** Private helpers
  // ***************************

  protected def addValues(key: IdentityWrapper, valuesMap: Map[String, Any]) = valuesMap ++= this.getValueMap(key)

  protected def getValueMap(key: IdentityWrapper): Map[String, Any] = this.getValueMap(key, true)

  protected def getValueMap(key: IdentityWrapper, create: Boolean): Map[String, Any] = {
    var value: Map[String, Any] = null
    if (!attributeMap.containsKey(key)) {
      if (create) {
        value = Map[String, Any]()
        this.attributeMap(key) = value
      }
    } else {
      value = attributeMap(key)
    }
    value
  }

  protected def addParentChild() {
    if (parent != null) {
      this.parent.addChild(this)
    }
  }

  protected def removeFromParent() {
    if (parent != null) {
      this.parent.removeChild(this);
    }
  }

  def addChild(child: ObjectTransactionImpl) = children.add(child)
  def removeChild(child: ObjectTransactionImpl) = children -= (child)

  // ***************************
  // ** #Object
  // ***************************

  override def toString(): String = {
    return this.getClass().getSimpleName() + s": $id   state: $state  owner: $owner  parent: $parent"
  }

  /**
   * Transactional clone implementation.
   *
   * Implementacion del clone transaccional. Aca no solo hay que clonar el mapa de la transaccion actual,
   * sino que el nuevo mapa debe ser la union de los mapas de atributos del objeto sourceObject en todas las
   * transacciones.
   *
   * @see ObjectTransactionManager.cloneAttributes()
   */
  def cloneAttributes(sourceObject: Object, targetObject: Object) {
    // copio los atributos transaccionales del objeto original al objeto clonado.
    this.attributeMap.put(IdentityWrapper.wrapKey(targetObject), this.getAllValueMaps(IdentityWrapper.wrapKey(sourceObject)));
  }

  /**
   * Recolecta y une todos los mapas de todas las transacciones para la clave sourceKey.
   *
   * @param sourceKey la clave del objeto del cual se desea obtener los atributos.
   * @return la union de todos los mapas de las transacciones donde este objeto figura.
   */
  protected def getAllValueMaps(sourceKey: IdentityWrapper): Map[String, Any] = {
    var unionMap = Map[String, Any]();
    unionMap ++= (this.getValueMap(sourceKey));
    if (this.parent != null) {
      unionMap.++=(this.parent.getAllValueMaps(sourceKey));
    }
    return unionMap;
  }

  protected def logger = ObjectTransactionImpl.LOG
  def getId() = this.id
  def getOwner() = this.owner
  def setState(state: String) = this.state = state
}