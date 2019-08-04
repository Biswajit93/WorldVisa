package com.worldvisa.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "WorkExperience")
public class WorkExperience {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int workExperienceID;
	
	public String Designation;
	
	public String EmployerNameandAddress;
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date ToDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date FromDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reportId",  nullable = false)
    private UserDetails userDetails;

	public String getDesignation() {
		return Designation;
	}

	public void setDesignation(String designation) {
		Designation = designation;
	}

	public String getEmployerNameandAddress() {
		return EmployerNameandAddress;
	}

	public void setEmployerNameandAddress(String employerNameandAddress) {
		EmployerNameandAddress = employerNameandAddress;
	}

	public int getWorkExperienceID() {
		return workExperienceID;
	}

	public void setWorkExperienceID(int workExperienceID) {
		this.workExperienceID = workExperienceID;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
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
