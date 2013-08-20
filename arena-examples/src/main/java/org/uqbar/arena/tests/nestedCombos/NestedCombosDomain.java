package org.uqbar.arena.tests.nestedCombos;
 
import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.utils.Observable;

@Observable
public class NestedCombosDomain {
	private Country country;
	private Province province;
	private List<Country> possibleCountries = new ArrayList<Country>();
	private List<Province> possibleProvinces = new ArrayList<Province>();
	private int times = 0;

	public NestedCombosDomain() {
		this.country = this.addCountry("Argentina", "Buenos Aires", "Córdoba",
				"Santa Fé");
		this.addCountry("Bolivia", "Cochabamba", "Potosí", "La Paz");
	}

	public void deleteProvince() {
		this.possibleProvinces.remove(this.province);
	}

	public void changed() {
		this.times++;
	}

	public Country addCountry(String name, String... provinces) {
		Country country = new Country(name);
		for (String provinceName : provinces) {
			country.addProvince(provinceName);
		}
		this.possibleCountries.add(country);
		return country;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
		this.possibleProvinces = country.provinces();
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public List<Country> getPossibleCountries() {
		return possibleCountries;
	}

	public void setPossibleCountries(List<Country> possibleCountries) {
		this.possibleCountries = possibleCountries;
	}

	public List<Province> getPossibleProvinces() {
		return possibleProvinces;
	}

	public void setPossibleProvinces(List<Province> possibleProvinces) {
		this.possibleProvinces = possibleProvinces;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}
}
