package org.uqbar.lacar.ui.impl.jface.bindings;

import java.beans.PropertyDescriptor;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.set.SetDiff;
import org.uqbar.arena.isolation.IsolationLevelEvents;

import com.uqbar.aop.transaction.ObjectTransactionManager;
import com.uqbar.apo.APOConfig;
import com.uqbar.common.transaction.ObjectTransaction;

public class JavaBeanTransacionalObservableSet extends ArenaJavaBeanObservableSet {
	  private final String isolationKey = "apo.poo.isolationLevel";
	  private ObjectTransaction objectTransactionImpl = ObjectTransactionManager.getTransaction();
	  private IsolationLevelEvents isolationLevelEvents = IsolationLevelEvents.valueOf(APOConfig.getProperty(isolationKey).value());

	public JavaBeanTransacionalObservableSet(Realm realm, Object object,
			PropertyDescriptor descriptor, Class<?> elementType) {
		super(realm, object, descriptor, elementType);
	}
	
	@Override
	public void fireSetChange(SetDiff diff) {
	    if (this.isolationLevelEvents.check(this.objectTransactionImpl)) {
	      super.fireSetChange(diff);
	    }
	  }

}
