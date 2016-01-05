package org.uqbar.arena.bootstrap;

public abstract class CollectionBasedBootstrap implements Bootstrap {

	@Override
	public boolean isPending() {
		return true;
	}
	
}
