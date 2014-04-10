package org.uqbar.arena.jface.app

import org.eclipse.core.databinding.observable.Realm
import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.swt.widgets.Display
import org.uqbar.lacar.ui.model.ApplicationRunner
import org.uqbar.lacar.ui.impl.jface.builder.windows.JFaceWindowBuilder

/**
 * Implementacion de {@link ApplicationRunner} para jface.
 * @author npasserini
 * @author jfernandes
 */
class JFaceApplicationBuilder extends ApplicationRunner {
  var display = new Display();

  override def run(application: Runnable) {
    Realm.runWithDefault(SWTObservables.getRealm(display), application)
    display.dispose
  }

  override def createWindow() = new JFaceWindowBuilder()

}