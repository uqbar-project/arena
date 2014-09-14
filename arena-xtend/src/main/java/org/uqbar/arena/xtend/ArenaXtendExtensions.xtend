package org.uqbar.arena.xtend

import org.uqbar.arena.bindings.ObservableProperty
import org.uqbar.lacar.ui.model.bindings.Observable
import org.uqbar.lacar.ui.model.bindings.ViewObservable
import org.eclipse.xtext.xbase.lib.Functions.Function1

import static org.uqbar.arena.xtend.ArenaTypeSafeBindingExtensions.calculateObservable

/**
 * Extension methods for Arena.
 * Usually operator overloading and methods for using Arena
 * with a sort of internal DSL. For example for bindings.
 * 
 * @author jfernandes
 */
class ArenaXtendExtensions {
	
	def static operator_spaceship(ViewObservable value, String property) {
		value.bindTo(new ObservableProperty(value.view.containerModelObject as Object, property))
	}
	
	def static <M,R> operator_spaceship(ViewObservable obs, Function1<M,R> propertyBinder) {
		obs <=> calculateObservable(obs.view.containerModelObject, propertyBinder)
	}
	
	def static bindTo(ViewObservable viewAttribute, Observable model) {
		viewAttribute.view.addBinding(model, viewAttribute)
	}
	
}