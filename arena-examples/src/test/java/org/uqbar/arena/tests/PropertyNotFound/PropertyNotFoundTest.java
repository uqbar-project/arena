package org.uqbar.arena.tests.PropertyNotFound;

import org.junit.Test;

public class PropertyNotFoundTest {
	@Test
	public void testPanelWithoutLayoutShouldGiveAGoodMessage() throws Exception {
		PropertyNotFoundWindow.main(new String[0]);
	}
}

