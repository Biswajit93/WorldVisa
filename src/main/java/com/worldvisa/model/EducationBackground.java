package com.worldvisa.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "EducationBackground")
public class EducationBackground {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int educationBackgroundID;
	
	public String Discipline;
	
	public String Degree;
	
	public String University;	
	
	public Date ToDate;	
	
	public Date FromDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reportId",  nullable = false)
    private UserDetails userDetails;

	public String getDiscipline() {
		return Discipline;
	}

	public void setDiscipline(String discipline) {
		Discipline = discipline;
	}

	public String getDegree() {
		return Degree;
	}

	public void setDegree(String degree) {
		Degree = degree;
	}

	public String getUniversity() {
		return University;
	}

	public void setUniversity(String university) {
		University = university;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public int getEducationBackgroundID() {
		return educationBackgroundID;
	}

	public void setEducationBackgroundID(int educationBackgroundID) {
		this.educationBackgroundID = educationBackgroundID;
	}

	public Date getToDate() {
		return ToDate;
	}

	public void setToDate(Date toDate) {
		ToDate = toDate;
	}

	public Date getFromDate() {
		return FromDate;
	}

	public void setFromDate(Date fromDate) {
		FromDate = fromDate;
	}
	
}
