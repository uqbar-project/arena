package org.uqbar.arena.isolation;

import org.uqbar.aop.transaction.ObjectTransactionManager;
import org.uqbar.common.transaction.ObjectTransaction;

public enum IsolationLevelEvents {
	
	FIRE_UNCOMMITTED("FIRE_UNCOMMITTED"){

		@Override
		public boolean check(ObjectTransaction objectTransaction) {
			return true;
		}
		
	},
	
	FIRE_COMMITTED("FIRE_COMMITTED"){

		@Override
		public boolean check(ObjectTransaction objectTransaction) {
			return objectTransaction.getId() >= ObjectTransactionManager.getTransaction().getId();
		}
		
	},
	
	SERIALIZABLE("SERIALIZABLE"){

		@Override
		public boolean check(ObjectTransaction objectTransaction) {
			return objectTransaction.getId() == ObjectTransactionManager.getTransaction().getId();
		}
		
	};
	

	private final String name;
	private IsolationLevelEvents(final String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}

	public abstract boolean check(ObjectTransaction objectTransaction);
}
