package org.uqbar.arena.examples.conversor.xtend.typesafe

import java.util.List
import org.eclipse.xtext.xbase.lib.Functions.Function1
import org.eclipse.xtext.xbase.lib.Pair
import org.eclipse.xtext.xbase.lib.Procedures.Procedure0
import org.mockito.internal.InternalMockHandler
import org.mockito.internal.configuration.ClassPathLoader
import org.mockito.internal.creation.settings.CreationSettings
import org.mockito.invocation.Invocation
import org.mockito.invocation.MockHandler
import org.mockito.stubbing.Answer
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Control
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.widgets.Selector
import org.uqbar.arena.widgets.TextBox
import org.uqbar.arena.windows.Window

/**
 * Arena extensions to provide type-safe bindings.
 * That is without using strings for property names but closures.
 * 
 * @author jfernandes
 */
class ArenaTypeSafeBindingExtensions {

	def <M,R> bindValue(Control control, Function1<M,R> propertyBinder) {
		// createMock
		val Class<M> concreteModelType = control.containerModelObject.class as Class<M>
		val handler = createInvocationHandler()
		val M mock = createMockFor(concreteModelType, handler) 
		
		// call closure
		propertyBinder.apply(mock)
		
		//TODO: inspect mock for getter calls, register binding.
		control.bindValueToProperty(handler.getPropertyName)
	}
	
	def createInvocationHandler() {
		new ArenaMockHandler
	}
	
	def <T> T createMockFor(Class<T> type, MockHandler handler) {
		return ClassPathLoader.getMockMaker().createMock(createMockCreationSettings(type), handler)
	}
	
	def <T> createMockCreationSettings(Class<T> typeToMock) {
		val CreationSettings<T> settings = new CreationSettings<T>();
        settings.setTypeToMock(typeToMock);
        return settings;
	}
	
	// ****************************
	// ** factory methods
	// ****************************
	
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
			onClick = onC
		]
	}
	
}

/**
 * MockHandler implementation for arena type-safe bindings.
 * 
 * @author jfernandes
 */
class ArenaMockHandler implements InternalMockHandler<Object> {
	@Property String propertyName
	
	override Object handle(Invocation invocation) throws Throwable {
		val m = invocation.getMethod()
		var propName = m.name.substring(3)
		this.propertyName = Character.toLowerCase(propName.charAt(0)) + propName.substring(1)
		
		return null
	}
	
	override getInvocationContainer() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override getMockSettings() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override void setAnswersForStubbing(List<Answer> answers) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override voidMethodStubbable(Object mock) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
}