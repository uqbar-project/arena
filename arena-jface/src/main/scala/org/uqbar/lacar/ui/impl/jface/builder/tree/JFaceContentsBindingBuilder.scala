package org.uqbar.lacar.ui.impl.jface.builder.tree

import org.uqbar.lacar.ui.model.BindingBuilder
import org.uqbar.arena.bindings.ValueTransformer


class JFaceContentsBindingBuilder(list:JFaceTreeBuilder[_]) extends BindingBuilder {
  private var treeViewer = list.getJFaceTreeViewer 

	def observeProperty(model:Object, parentPropertyName:String, childPropertyName:String) = {
		// ATENCION, el content provider DEBE ser asignado ANTES que el input.
		treeViewer.setContentProvider(new TreeContentProvider(parentPropertyName, childPropertyName))
		treeViewer.setInput(model)
	}

	override def adaptWith[M,V](transformer:ValueTransformer[M, V]) = 
		throw new UnsupportedOperationException("No está preparado para tener value transformers.")
	
	override def modelToView[M,V](transformer : org.apache.commons.collections15.Transformer[M,V]) =
	  throw new UnsupportedOperationException("No está preparado para tener transformers.")
	
	override def viewToModel[M,V](transformer : org.apache.commons.collections15.Transformer[V,M]) = {
	  throw new UnsupportedOperationException("No está preparado para tener transformers.")
	}

	override def observeProperty(model:Object, propertyName:String) =
		throw new UnsupportedOperationException("No se puede usar este binding, utilice " +
				" observeProperty(ObservableObject model, String parentPropertyName, String childPropertyName)");
	
	override def observeErrors() = 
	  throw new UnsupportedOperationException("Error observing is not implemented for content bindings.")

	override def build() {	}

}
