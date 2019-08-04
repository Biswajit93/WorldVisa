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
@Table(name = "TestScores")
public class TestScores {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int testScoresID;
	
	public String TestName;
	
	public String TestScore;
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date TestDate;

	public String getTestName() {
		return TestName;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reportId",  nullable = false)
    private UserDetails userDetails;

	public void setTestName(String testName) {
		TestName = testName;
	}

	public String getTestScore() {
		return TestScore;
	}

	public void setTestScore(String testScore) {
		TestScore = testScore;
	}

	public Date getTestDate() {
		return TestDate;
	}

	public void setTestDate(Date testDate) {
		TestDate = testDate;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public int getTestScoresID() {
		return testScoresID;
	}

	public void setTestScoresID(int testScoresID) {
		this.testScoresID = testScoresID;
	}

}
