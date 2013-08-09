package org.uqbar.lacar.ui.impl.jface.tree;

import org.eclipse.jface.viewers.LabelProvider;
import org.uqbar.commons.utils.ReflectionUtils;

public class ReflectionLabelProvider extends LabelProvider {
	
	private final String propertyName;

	public ReflectionLabelProvider(String propertyName) {
		this.propertyName = propertyName;
	}
	
	@Override
	public String getText(Object element) {
		if(propertyName != null){
			return ReflectionUtils.invokeGetter(element, propertyName) + "";
		}else{
			return element.toString();
		}
	}

}
