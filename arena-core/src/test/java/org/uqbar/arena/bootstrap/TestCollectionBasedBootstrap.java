package org.uqbar.arena.bootstrap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Probamos un bootstrap vacío en memoria 
 * 
 * @author fernando
 *
 */
public class TestCollectionBasedBootstrap {

	Bootstrap bootstrapEnMemoria;
	
	@Before
	public void setUp() throws Exception {
		bootstrapEnMemoria = new CollectionBasedBootstrap() {
			
			@Override
			public void run() {
				// no hacemos nada para el test
			}
		};
	}

	@Test
	public void testRunUnBootstrapCollectionBasedNoSeRompe() {
		bootstrapEnMemoria.run();
		Assert.assertTrue(true);
	}

	@Test
	public void testRunUnBootstrapCollectionBasedSiempreSeEjecuta() {
		Assert.assertTrue(bootstrapEnMemoria.isPending());
	}

}
