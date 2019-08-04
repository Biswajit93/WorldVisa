package com.worldvisa.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "UserDetails")
public class UserDetails{

	@Id
	private String reportId;
	
	private String areaOfInterest;

	private String editableByClient;
	
	private String requestMode;
	
	private String status;
	
	public UserDetails() {
		languageProficiency = new ArrayList<LanguageProficiency>();
		testScoresList = new ArrayList<TestScores>();
		familyDetailsList  = new ArrayList<FamilyDetails>();
		relativesAbroadList  = new ArrayList<RelativesAbroad>();
		workExperienceList  = new ArrayList<WorkExperience>();
		countryPreferencesList  = new ArrayList<CountryPreferences>();
		paymentList  = new ArrayList<Payment>();
		paymentTransactionList  = new ArrayList<PaymentTransaction>();
		educationBackgroundList = new ArrayList<EducationBackground>();
		notification = new ArrayList<UserNotification>();
		userPaymentDates = new ArrayList<UserPaymentDates>();
		invoice = new ArrayList<Invoice>();
	}

	private String hearAboutUs;
	
	private String friendName;
	
	private String mobileNumber;
	
	private String raisedBy;
	
	private Timestamp raisedTime;
	
	private String assignedTo;
	
	@OneToOne(mappedBy = "userDetails", cascade = CascadeType.ALL, 
             optional = false)
	private UserInformation userInformation;
	
		
	@OneToMany(
	        mappedBy = "userDetails", 
	        cascade = CascadeType.ALL, 
	        orphanRemoval = true
	    )
	private List<LanguageProficiency> languageProficiency;
	
	@OneToMany(
	        mappedBy = "userDetails", 
	        cascade = CascadeType.ALL, 
	        orphanRemoval = true
	    )
	private List<TestScores> testScoresList;
	
	@OneToMany(
	        mappedBy = "userDetails", 
	        cascade = CascadeType.ALL, 
	        orphanRemoval = true
	    )
	private List<FamilyDetails> familyDetailsList;
	
	@OneToMany(
	        mappedBy = "userDetails", 
	        cascade = CascadeType.ALL, 
	        orphanRemoval = true
	    )
	private List<RelativesAbroad> relativesAbroadList;
	
	@OneToMany(
	        mappedBy = "userDetails", 
	        cascade = CascadeType.ALL, 
	        orphanRemoval = true
	    )
	private List<EducationBackground> educationBackgroundList;
	
	@OneToMany(
	        mappedBy = "userDetails", 
	        cascade = CascadeType.ALL, 
	        orphanRemoval = true
	    )
	private List<WorkExperience> workExperienceList;
	
	@OneToMany(
	        mappedBy = "userDetails", 
	        cascade = CascadeType.ALL, 
	        orphanRemoval = true
	    )
	private List<CountryPreferences> countryPreferencesList;
	
	@OneToMany(
	        mappedBy = "userDetails", 
	        cascade = CascadeType.ALL, 
	        orphanRemoval = true
	    )
	private List<Payment> paymentList;
	
	@OneToMany(
	        mappedBy = "userDetails", 
	        cascade = CascadeType.ALL, 
	        orphanRemoval = true
	    )
	private List<PaymentTransaction> paymentTransactionList;
	
	@OneToMany(
	        mappedBy = "userDetails", 
	        cascade = CascadeType.ALL, 
	        orphanRemoval = true
	    )
	private List<UserNotification> notification;
	
	
	@OneToMany(
	        mappedBy = "userDetails", 
	        cascade = CascadeType.ALL, 
	        orphanRemoval = true
	    )
	private List<UserPaymentDates> userPaymentDates;
	
	@OneToMany(
	        mappedBy = "userDetails", 
	        cascade = CascadeType.ALL, 
	        orphanRemoval = true
	    )
	private List<Invoice> invoice;
	
	@Transient
	private List<String> countryList;
	
	private int expiry;
	
	private boolean paymentDone =false;
	
	public String getAreaOfInterest() {
		return areaOfInterest;
	}

	public void setAreaOfInterest(String areaOfInterest) {
		this.areaOfInterest = areaOfInterest;
	}

	public String getEditableByClient() {
		return editableByClient;
	}

	public void setEditableByClient(String editableByClient) {
		this.editableByClient = editableByClient;
	}

	public String getRequestMode() {
		return requestMode;
	}

	public void setRequestMode(String requestMode) {
		this.requestMode = requestMode;
	}

	public String getHearAboutUs() {
		return hearAboutUs;
	}

	public void setHearAboutUs(String hearAboutUs) {
		this.hearAboutUs = hearAboutUs;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}
	
	public String getRaisedBy() {
		return raisedBy;
	}

	public void setRaisedBy(String raisedBy) {
		this.raisedBy = raisedBy;
	}

	public Timestamp getRaisedTime() {
		return raisedTime;
	}

	public void setRaisedTime(Timestamp raisedTime) {
		this.raisedTime = raisedTime;
	}

	public List<LanguageProficiency> getLanguageProficiency() {
		return languageProficiency;
	}

	public void setLanguageProficiency(List<LanguageProficiency> languageProficiency) {
		this.languageProficiency = languageProficiency;
	}

	public List<String> getCountryList() {
		return countryList;
	}

	public void setCountryList(List<String> countryList) {
		this.countryList = countryList;
	}

	public List<TestScores> getTestScoresList() {
		return testScoresList;
	}

	public void setTestScoresList(List<TestScores> testScoresList) {
		this.testScoresList = testScoresList;
	}

	public List<FamilyDetails> getFamilyDetailsList() {
		return familyDetailsList;
	}

	public void setFamilyDetailsList(List<FamilyDetails> familyDetailsList) {
		this.familyDetailsList = familyDetailsList;
	}

	public List<RelativesAbroad> getRelativesAbroadList() {
		return relativesAbroadList;
	}

	public void setRelativesAbroadList(List<RelativesAbroad> relativesAbroadList) {
		this.relativesAbroadList = relativesAbroadList;
	}

	public List<EducationBackground> getEducationBackgroundList() {
		return educationBackgroundList;
	}

	public void setEducationBackgroundList(List<EducationBackground> educationBackgroundList) {
		this.educationBackgroundList = educationBackgroundList;
	}

	public List<WorkExperience> getWorkExperienceList() {
		return workExperienceList;
	}

	public void setWorkExperienceList(List<WorkExperience> workExperienceList) {
		this.workExperienceList = workExperienceList;
	}

	public List<CountryPreferences> getCountryPreferencesList() {
		return countryPreferencesList;
	}

	public void setCountryPreferencesList(List<CountryPreferences> countryPreferencesList) {
		this.countryPreferencesList = countryPreferencesList;
	}

	public List<Payment> getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(List<Payment> paymentList) {
		this.paymentList = paymentList;
	}

	public List<PaymentTransaction> getPaymentTransactionList() {
		return paymentTransactionList;
	}

	public void setPaymentTransactionList(List<PaymentTransaction> paymentTransactionList) {
		this.paymentTransactionList = paymentTransactionList;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public UserInformation getUserInformation() {
		return userInformation;
	}

	public void setUserInformation(UserInformation userInformation) {
		this.userInformation = userInformation;
	}

	public List<UserNotification> getNotification() {
		return notification;
	}

	public void setNotification(List<UserNotification> notification) {
		this.notification = notification;
	}

	public List<UserPaymentDates> getUserPaymentDates() {
		return userPaymentDates;
	}

	public void setUserPaymentDates(List<UserPaymentDates> userPaymentDates) {
		this.userPaymentDates = userPaymentDates;
	}

	public int getExpiry() {
		return expiry;
	}

	public void setExpiry(int expiry) {
		this.expiry = expiry;
	}

	public boolean isPaymentDone() {
		return paymentDone;
	}

	public void setPaymentDone(boolean paymentDone) {
		this.paymentDone = paymentDone;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Invoice> getInvoice() {
		return invoice;
	}

	public void setInvoice(List<Invoice> invoice) {
		this.invoice = invoice;
	}


}
