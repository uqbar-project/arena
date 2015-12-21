package org.uqbar.lacar.ui.impl.jface.builder

import org.uqbar.lacar.ui.impl.jface.builder.traits.JFaceContainer
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Text

class JFacePasswordBuilder(container:JFaceContainer) 
extends AbstractJFaceTextBuilder(container, new Text(container.getJFaceComposite(), SWT.PASSWORD | SWT.BORDER)) 
{

}