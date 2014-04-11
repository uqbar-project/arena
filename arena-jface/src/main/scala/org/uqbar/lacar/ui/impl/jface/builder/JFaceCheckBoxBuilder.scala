package org.uqbar.lacar.ui.impl.jface.builder

import org.eclipse.swt.widgets.Button
import org.uqbar.lacar.ui.impl.jface.builder.traits.JFaceContainer
import org.uqbar.lacar.ui.impl.jface.builder.traits.SelectionAsValue
import org.uqbar.lacar.ui.impl.jface.builder.traits.SelectionAsValue

class JFaceCheckBoxBuilder(jFaceWidget:Button, container:JFaceContainer) 
	extends JFaceControlBuilder[Button](container, jFaceWidget) 
	with SelectionAsValue {

}