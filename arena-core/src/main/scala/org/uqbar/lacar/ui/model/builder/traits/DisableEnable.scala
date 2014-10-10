package org.uqbar.lacar.ui.model.builder.traits

import org.uqbar.lacar.ui.model.BindingBuilder

/**
 * For controls that can be enabled/disable
 * @author jfernandes
 */
trait DisableEnable {

  /**
   * Starts the creation of a binding to the 'enabled' property of this Control with an observable property of the model
   * @return A {@link BindingBuilder} associated to this control, which allows to further configure the
   *         binding and has the ultimate responsibility of creating it.
   */
  def observeEnabled(): BindingBuilder

}