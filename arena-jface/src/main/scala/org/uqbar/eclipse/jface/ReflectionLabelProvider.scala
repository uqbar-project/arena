package org.uqbar.eclipse.jface

import org.eclipse.jface.viewers.LabelProvider
import org.uqbar.commons.utils.ReflectionUtils

class ReflectionLabelProvider(var propertyName:String) extends LabelProvider {

  override def getText(element:Object) = 
    if (propertyName != null) 
	  ReflectionUtils.invokeGetter(element, propertyName) + ""
    else
      element.toString
  
}