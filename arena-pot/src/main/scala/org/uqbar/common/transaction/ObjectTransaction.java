package org.uqbar.common.transaction;

/**
 * DOCME
 */
public interface ObjectTransaction {

	/**
	 * 
	 * @return
	 */
    public Long getId();

    /**
     * 
     * @return
     */
    public TaskOwner getOwner();

}