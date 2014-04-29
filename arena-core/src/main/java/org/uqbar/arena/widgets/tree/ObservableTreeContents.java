package org.uqbar.arena.widgets.tree;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.bindings.AbstractViewObservable;
import org.uqbar.lacar.ui.model.bindings.ViewObservable;

/**
 * 
 * @author npasserini
 */
public class ObservableTreeContents extends AbstractViewObservable<Tree, TreeBuilder<?>> {

	public ObservableTreeContents(Tree view) {
		super(view);
	}

	@Override
	public BindingBuilder createBinding(TreeBuilder<?> list) {
		return list.observeContents();
	}

}
