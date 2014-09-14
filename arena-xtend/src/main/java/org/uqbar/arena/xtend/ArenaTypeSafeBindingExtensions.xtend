package org.uqbar.arena.xtend

import java.util.List
import org.eclipse.xtext.xbase.lib.Functions.Function1
import org.mockito.internal.InternalMockHandler
import org.mockito.internal.configuration.ClassPathLoader
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
		// createMock
		val Class<M> concreteModelType = control.containerModelObject.class as Class<M>
		val handler = createInvocationHandler()
		val M mock = createMockFor(concreteModelType, handler) 
		
		// call closure
		propertyBinder.apply(mock)
		
		//TODO: inspect mock for getter calls, register binding.
		control.bindValueToProperty(handler.getPropertyName)
	}
	
	def protected createInvocationHandler() {
		new ArenaMockHandler
	}
	
	def protected <T> T createMockFor(Class<T> type, MockHandler handler) {
		return ClassPathLoader.getMockMaker().createMock(createMockCreationSettings(type), handler)
	}
	
	def protected <T> createMockCreationSettings(Class<T> typeToMock) {
		val CreationSettings<T> settings = new CreationSettings<T>();
        settings.setTypeToMock(typeToMock);
        return settings;
	}
	
}

/**
 * MockHandler implementation for arena type-safe bindings.
 * 
 * @author jfernandes
 */
//TODO: support nested properties
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