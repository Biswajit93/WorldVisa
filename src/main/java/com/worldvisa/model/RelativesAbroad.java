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
@Table(name = "RelativesAbroad")
public class RelativesAbroad {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int relativesAbroadID;
	
	public String CityandCountry;
	
	public String Relation;
	
	public String RelativeName;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reportId",  nullable = false)
    private UserDetails userDetails;
	
	public String getCityandCountry() {
		return CityandCountry;
	}

	public void setCityandCountry(String cityandCountry) {
		CityandCountry = cityandCountry;
	}

	public String getRelation() {
		return Relation;
	}

	public void setRelation(String relation) {
		Relation = relation;
	}

	public String getRelativeName() {
		return RelativeName;
	}

	public void setRelativeName(String relativeName) {
		RelativeName = relativeName;
	}

	public int getRelativesAbroadID() {
		return relativesAbroadID;
	}

	public void setRelativesAbroadID(int relativesAbroadID) {
		this.relativesAbroadID = relativesAbroadID;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

}
