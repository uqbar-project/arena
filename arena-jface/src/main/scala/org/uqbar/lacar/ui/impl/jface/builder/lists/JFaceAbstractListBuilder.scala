package org.uqbar.lacar.ui.impl.jface.builder.lists

import org.eclipse.jface.databinding.viewers.ViewersObservables
import org.eclipse.jface.viewers.AbstractListViewer
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Control
import org.uqbar.arena.jface.JFaceImplicits._
import org.uqbar.lacar.ui.impl.jface.JFaceContainer
import org.uqbar.lacar.ui.impl.jface.JFaceControlBuilder
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder
import org.uqbar.lacar.ui.impl.jface.lists.JFaceListItemsBindingBuilder
import org.uqbar.lacar.ui.model.Action
import org.uqbar.lacar.ui.model.ListBuilder

abstract class JFaceAbstractListBuilder[T, V <: AbstractListViewer, C <: Control](container:JFaceContainer) 
		extends JFaceControlBuilder[C](container)
		with ListBuilder[T]
{
  var viewer = this.createViewer(container.getJFaceComposite());
  
  protected def createViewer(jFaceComposite:Composite) : V
  
  override def observeValue() = 
		new JFaceBindingBuilder(this, ViewersObservables.observeSingleSelection(viewer)) {
			override def createBinding() = {
				val binding = super.createBinding
				onPack(new Action() {
					override def execute() = {
						// By the time of binding, it could happen that the contents of the list have not been
						// set. So the value binding is not reflected in the view. Updating the model on the
						// pack time allows to ensure that the view is updated (as we are sure that the
						// contents of the list are set now). This allows the user to configure the bindings
						// in whatever order he wants and it will always work.
						binding.updateModelToTarget
					}
				})
				binding
			}
		}

	override def observeItems() = new JFaceListItemsBindingBuilder(this)

	override def onSelection(action: Action) = {
		viewer addSelectionChangedListener(action)
		this
	}
	
	override def getControlLayout() = viewer.getControl

}