package org.uqbar.arena.xtend

import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.xbase.lib.Functions.Function1
import org.mockito.internal.InternalMockHandler
import org.mockito.internal.configuration.plugins.Plugins
import org.mockito.internal.creation.settings.CreationSettings
import org.mockito.invocation.Invocation
import org.mockito.invocation.MockHandler
import org.mockito.stubbing.Answer
import org.uqbar.arena.widgets.Control

/**
 * Arena extensions to provide type-safe bindings.
 * That is without using strings for property names but closures.
 * 
 * @author jfernandes
 */
class ArenaTypeSafeBindingExtensions {

	def <M,R> bindValue(Control control, Function1<M,R> propertyBinder) {
		val modelObservableAsString = calculateObservable(control.containerModelObject, propertyBinder)
		control.bindValueToProperty(modelObservableAsString)
	}
	
	def static <M,R> calculateObservable(Object model, Function1<M, R> propertyBinder) {
		val Class<M> concreteModelType = model.class as Class<M>
		val handler = createInvocationHandler()
		val M mock = createMockFor(concreteModelType, handler) 
		
		// call closure
		propertyBinder.apply(mock)
		
		//TODO: inspect mock for getter calls, register binding.
		handler.getPropertyName
	}
	
	def static createInvocationHandler() { new ArenaMockHandler }
	
	def static <T> T createMockFor(Class<T> type, MockHandler handler) {
		Plugins.getMockMaker.createMock(createMockCreationSettings(type), handler)
	}
	
	def static <T> createMockCreationSettings(Class<T> typeToMock) {
		new CreationSettings<T> => [
			setTypeToMock(typeToMock)
		]
	}
	
}

/**
 * MockHandler implementation for arena type-safe bindings.
 * 
 * @author jfernandes
 */
//TODO: support nested properties
@Accessors
class ArenaMockHandler implements InternalMockHandler<Object> {
	String propertyName
	
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