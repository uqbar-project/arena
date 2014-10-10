package org.uqbar.arena.examples.controls.binding.enable;

import java.util.Arrays;
import java.util.List;

import org.uqbar.commons.model.ObservableObject;

/**
 * @author jfernandes
 */
public class Address extends ObservableObject {
	private Country country;
	private String state;
	private String street;

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.setFieldValue("country", country);
	}

	public String getState() {
		return this.state;
	}
	
	public void setState(String state) {
		this.setFieldValue("state", state);
	}
	
	public String getStreet() {
		return this.street;
	}
	
	public void setStreet(String street) {
		this.setFieldValue("street", street);
	}
	
	public static List<Country> createCountries() {
		return Arrays.asList(
				new Country("Argentina"),
				new Country("Cuba"),
				new Country("Iran"),
				new Country("North Korea")
		);
	}

}
