package org.uqbar.lacar.ui.model;

import org.uqbar.arena.widgets.TextFilter;

/**
 * Special builder for {@link TextBox} controls
 * 
 * @author jfernandes
 */
public interface TextControlBuilder extends ControlBuilder{

	public void addTextFilter(TextFilter filter);
	public void selectFinalLine();
	
}
