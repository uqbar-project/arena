package org.uqbar.lacar.ui.impl.jface.bindings;

import org.eclipse.core.databinding.observable.masterdetail.IObservableFactory;
import org.eclipse.core.databinding.observable.set.SetDiff;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.internal.databinding.observable.masterdetail.DetailObservableSet;
import org.uqbar.arena.isolation.IsolationLevelEvents;

import com.uqbar.aop.transaction.ObjectTransactionManager;
import com.uqbar.apo.APOConfig;
import com.uqbar.common.transaction.ObjectTransaction;

public class DetailTransactionalObservableSet extends DetailObservableSet{
	
	private final String isolationKey = "aop.opo.isolationLevel";
	private ObjectTransaction objectTransactionImpl = ObjectTransactionManager.getTransaction();
	private IsolationLevelEvents isolationLevelEvents = IsolationLevelEvents.valueOf(APOConfig.getProperty(isolationKey).value());

	public DetailTransactionalObservableSet(IObservableFactory factory,
		  IObservableValue outerObservableValue, Object detailType) {
		super(factory, outerObservableValue, detailType);
	}
	
	@Override
	public void fireSetChange(SetDiff diff) {
	    if (this.isolationLevelEvents.check(this.objectTransactionImpl)) {
	      super.fireSetChange(diff);
	    }
	  }

}
