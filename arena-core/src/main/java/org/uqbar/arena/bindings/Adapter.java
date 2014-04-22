package org.uqbar.arena.bindings;

import org.uqbar.lacar.ui.model.BindingBuilder;

/**
 * A closure that configures a binding.
 *  
 * @author jfernandes
 */
public interface Adapter {

	public void configure(BindingBuilder binder);

}
