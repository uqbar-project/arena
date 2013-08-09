package org.uqbar.arena.widgets.tree;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.bindings.ViewObservable;

public class ObservableTreeContents implements ViewObservable<TreeBuilder<?>> {

	@Override
	public BindingBuilder createBinding(TreeBuilder<?> list) {
		return list.observeContents();
	}

}
