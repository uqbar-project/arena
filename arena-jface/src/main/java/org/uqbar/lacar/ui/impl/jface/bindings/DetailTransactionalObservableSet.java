package org.uqbar.lacar.ui.impl.jface.bindings;

import org.eclipse.core.databinding.observable.masterdetail.IObservableFactory;
import org.eclipse.core.databinding.observable.set.SetDiff;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.internal.databinding.observable.masterdetail.DetailObservableSet;
import org.uqbar.aop.transaction.ObjectTransactionManager;
import org.uqbar.apo.APOConfig;
import org.uqbar.arena.isolation.IsolationLevelEvents;
import org.uqbar.common.transaction.ObjectTransaction;
import org.uqbar.commons.model.utils.ReflectionUtils;

public class DetailTransactionalObservableSet extends DetailObservableSet{
	
	private final String isolationKey = "apo.poo.isolationLevel";
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
	
	public Object getCurrentValue(){
		return ReflectionUtils.readField(ReflectionUtils.readField(this, "innerObservableSet"), "object");
	}

}
