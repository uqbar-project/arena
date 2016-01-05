package org.uqbar.arena.bootstrap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestNullBootstrap {

	Bootstrap bootstrapVacio;
	
	@Before
	public void setUp() throws Exception {
		bootstrapVacio = new NullBootstrap();
	}

	@Test
	public void testRunUnBootstrapVacioNoSeRompe() {
		bootstrapVacio.run();
		Assert.assertTrue(true);
	}

	@Test
	public void testRunUnBootstrapVacioNoSeEjecuta() {
		Assert.assertFalse(bootstrapVacio.isPending());
	}

}
