package com.worldvisa.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "CaseStatus")
public class CaseStatus {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int caseStatusId;
	
	private String setBy;
	private Timestamp setOn;
	private String communication;
	private String description;
	private String subStatus;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reportId",  nullable = false)
    private UserDetails userDetails;
	
	public int getCaseStatusId() {
		return caseStatusId;
	}
	public void setCaseStatusId(int caseStatusId) {
		this.caseStatusId = caseStatusId;
	}
	public String getSetBy() {
		return setBy;
	}
	public void setSetBy(String setBy) {
		this.setBy = setBy;
	}
	public Timestamp getSetOn() {
		return setOn;
	}
	public void setSetOn(Timestamp setOn) {
		this.setOn = setOn;
	}
	public String getCommunication() {
		return communication;
	}
	public void setCommunication(String communication) {
		this.communication = communication;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSubStatus() {
		return subStatus;
	}
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}
	public UserDetails getUserDetails() {
		return userDetails;
	}
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
	
	
}
