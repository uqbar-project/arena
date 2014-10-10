package com.uqbar.common.transaction;


/**
 * Un objeto que puede ser dueño de una transaccion de objetos. (es decir, iniciarla, comitearla,
 * rollbackearla a piacere).
 * 
 * @author rgomez
 * @author jpicasso
 */
public interface TaskOwner{
    
	/**
	 * 
	 * @return
	 */
    public boolean isTransactional();
    
    /**
     * 
     */
    public void setTransaction(ObjectTransaction transaction);

    /**
     * 
     * @return
     */
    public ObjectTransaction getTransaction();
    
    public String getName();
    
}