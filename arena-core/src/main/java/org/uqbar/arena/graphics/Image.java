package org.uqbar.arena.graphics;

/**
 * @author jfernandes
 */
public class Image {
	private String pathToFile;

	public Image(String pathToFile) {
		this.pathToFile = pathToFile;
	}

	public String getPathToFile() {
		return pathToFile;
	}

	public void setPathToFile(String pathToFile) {
		this.pathToFile = pathToFile;
	}

}