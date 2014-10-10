package org.uqbar.arena.actions;

import org.uqbar.lacar.ui.model.Action;

/**
 * Decorates an {@link Action} object to make it asynchronous.
 * 
 * @author jfernandes
 */
public class AsyncActionDecorator implements Action {
	private Action decoratee;

	public AsyncActionDecorator(Action decoratee) {
		super();
		this.decoratee = decoratee;
	}

	@Override
	public void execute() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				decoratee.execute();
			}
		}).start();
	}

}
