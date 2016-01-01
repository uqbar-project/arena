package org.uqbar.arena.xtend

import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.widgets.Selector
import org.uqbar.arena.widgets.TextBox
import org.uqbar.arena.windows.Window

import org.eclipse.xtext.xbase.lib.Procedures.Procedure0
import org.eclipse.xtext.xbase.lib.Functions.Function1

/**
 * Extension as factory methods to create arena applications
 * as a kind of internal DSL.
 * 
 * @author jfernandes 
 */
class ArenaXTendFactory {
	extension ArenaTypeSafeBindingExtensions = new ArenaTypeSafeBindingExtensions
	
	def <M,R> binding(Window<M> window, Function1<M,R> function1) {
		function1;
	}
	
	def asLabel(String string, Panel panel) {
		new Label(panel).text = string
	}
	
	def <M,R> asTextBoxIn(Function1<M,R> binding, Panel panel) {
		new TextBox(panel) => [
			bindValue(binding)
		]
	}
	
	def <M,R> asSelectorIn(Function1<M,R> binding, Panel panel) {
		new Selector<R>(panel) => [
			bindValue(binding)
		]
	}
	
	def <M,R> asLabelIn(Function1<M,R> binding, Panel panel) {
		new Label(panel) => [
			bindValue(binding)
		]
	}
	
	def asButtonIn(Pair<String,Procedure0> captionAndAction, Panel panel) {
		val onC = captionAndAction.value  // keep this line to avoid compilation error in java. Seems like a bug in xtend
		new Button(panel) => [
			caption = captionAndAction.key
			onClick(onC)
		]
	}
	
}