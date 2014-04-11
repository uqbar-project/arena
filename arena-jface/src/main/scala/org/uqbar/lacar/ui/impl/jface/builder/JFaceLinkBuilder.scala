package org.uqbar.lacar.ui.impl.jface.builder

import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Link
import org.uqbar.lacar.ui.impl.jface.JFaceContainer
import org.uqbar.lacar.ui.impl.jface.builder.traits.JFaceClickable
import org.uqbar.lacar.ui.impl.jface.builder.traits.JFaceWithCaption
import org.uqbar.lacar.ui.model.builder.LinkBuilder

/**
 * @author jfernandes
 */
class JFaceLinkBuilder(context : JFaceContainer) 
	extends JFaceSkinnableControlBuilder[Link](context, new Link(context.getJFaceComposite(), SWT.NONE)) 
	with LinkBuilder
	with JFaceClickable
	with JFaceWithCaption {
  
	// Esto no tiene sentido. Pasar a traits y que no este en e link
	override def observeValue = 
	  throw new UnsupportedOperationException("Se intentó observar la propiedad 'value' de un Link, que no tiene dicha propiedad")
	
}