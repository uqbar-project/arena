package org.uqbar.arena.widgets.tables;

import org.apache.commons.collections15.Transformer;

/**
 * @author jfernandes
 */
public class NoopTransformer implements Transformer {
	public static final NoopTransformer SHARED_INSTANCE = new NoopTransformer();

	@Override
	public Object transform(Object element) {
		return element;
	}

}
