package com.worldvisa.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CountryPreferences")
public class CountryPreferences {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int countryPreferencesID;
	
	private String country;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reportId",  nullable = false)
    private UserDetails userDetails;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getCountryPreferencesID() {
		return countryPreferencesID;
	}

	public void setCountryPreferencesID(int countryPreferencesID) {
		this.countryPreferencesID = countryPreferencesID;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

}
