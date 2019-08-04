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
@Table(name = "FamilyDetails")
public class FamilyDetails {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int familyDetailsID;
	
	public String Name;
	
	public String Relation;
	
	public String DateofBirth;
	
	public String HightestDegree;
	
	public String WorkExperience;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reportId",  nullable = false)
    private UserDetails userDetails;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getRelation() {
		return Relation;
	}

	public void setRelation(String relation) {
		Relation = relation;
	}

	public String getDateofBirth() {
		return DateofBirth;
	}

	public void setDateofBirth(String dateofBirth) {
		DateofBirth = dateofBirth;
	}

	public String getHightestDegree() {
		return HightestDegree;
	}

	public void setHightestDegree(String hightestDegree) {
		HightestDegree = hightestDegree;
	}

	public String getWorkExperience() {
		return WorkExperience;
	}

	public void setWorkExperience(String workExperience) {
		WorkExperience = workExperience;
	}

	public int getFamilyDetailsID() {
		return familyDetailsID;
	}

	public void setFamilyDetailsID(int familyDetailsID) {
		this.familyDetailsID = familyDetailsID;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
	
}
