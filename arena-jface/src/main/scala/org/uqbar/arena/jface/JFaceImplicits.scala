package org.uqbar.arena.jface

import org.eclipse.core.databinding.observable.value.ComputedValue
import org.eclipse.swt.events.ModifyListener
import org.eclipse.swt.events.ModifyEvent
import org.eclipse.swt.events.VerifyListener
import org.eclipse.swt.events.VerifyEvent
import org.uqbar.arena.widgets.TextInputEvent
import org.eclipse.swt.widgets.Text
import java.util.concurrent.Callable
import org.uqbar.lacar.ui.model.Action
import org.eclipse.jface.viewers.ISelectionChangedListener
import org.eclipse.jface.viewers.StructuredSelection
import org.eclipse.jface.viewers.SelectionChangedEvent
import org.eclipse.core.databinding.observable.value.IValueChangeListener
import org.eclipse.core.databinding.observable.value.ValueChangeEvent
import org.eclipse.swt.widgets.Event
import org.eclipse.swt.widgets.Listener

/**
 * @author jfernandes
 */
object JFaceImplicits {

  implicit def closureToComputedValue(closure: () => Object): ComputedValue = {
    new ComputedValue() {
      override def calculate(): Object = closure()
    }
  }
  
  implicit def closureToListener(closure: (Event) => Unit) = new Listener() {
    override def handleEvent(e:Event) = closure(e)
  }

  implicit def closureToModifyListener(closure: (ModifyEvent) => Unit): ModifyListener = {
    new ModifyListener {
      override def modifyText(e: ModifyEvent) {
        closure(e)
      }
    }
  }

  implicit def closureToVerifyListener(closure: (VerifyEvent) => Unit): VerifyListener = {
    new VerifyListener {
      override def verifyText(e: VerifyEvent) {
        closure(e)
      }
    }
  }

  implicit def verifyEventToTextInputEvent(event: VerifyEvent) =
    new TextInputEvent(event.start, event.end, event.text,
      new Callable[String]() {
        override def call() = {
          event.widget.asInstanceOf[Text].getText
        }
      });
  
  implicit def actionToSelectionListener(action:Action) =
    new ISelectionChangedListener() {
	  override def selectionChanged(e:SelectionChangedEvent) = {
	    val selection = e.getSelection.asInstanceOf[StructuredSelection]
		if (!selection.isEmpty) {
			action.execute
		}
	  }
    }
  
  implicit def closure0ToIValueChangeListener(closure : () => Unit) = 
    new IValueChangeListener() {
	  override def handleValueChange(event:ValueChangeEvent) = closure()
    }
  
  implicit def closure1ToIValueChangeListener(closure : (ValueChangeEvent) => Unit) = 
    new IValueChangeListener() {
	  override def handleValueChange(event:ValueChangeEvent) = closure(event)
    }

}

