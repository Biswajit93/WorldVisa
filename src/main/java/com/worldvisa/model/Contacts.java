package com.worldvisa.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Contacts")
public class Contacts {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int contactId;
	
	private String name;
	private String gender;
	private Double age;
	private String desiredCourseLevel;
	private String fieldOfInterest;
	private String desiredCountries;
	private String planToGo;
	private String SourceOfFunding;
	private String graduationStatus;
	private String graduationCourse;
	private Double graduationMarks;
	private String graduationMonth;
	private Double graduationYear;
	private String stdXIIStream;
	private Double stdXIIMarks;
	private Double stdXIIYear;
	private String examTaken1;
	private String examTaken2;
	private String examTaken3;
	private String preferredTimeToCall;
	private String currentLocation;
	private String userComments;
	private String dateOfRegistration;
	private String isInNDNCList;
	private String email;
	private Double mobile;
	
	
	private int contactFileId;
	
	public int getContactId() {
		return contactId;
	}
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getContactFileId() {
		return contactFileId;
	}
	public void setContactFileId(int contactFileId) {
		this.contactFileId = contactFileId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Double getAge() {
		return age;
	}
	public void setAge(Double age) {
		this.age = age;
	}
	public String getDesiredCourseLevel() {
		return desiredCourseLevel;
	}
	public void setDesiredCourseLevel(String desiredCourseLevel) {
		this.desiredCourseLevel = desiredCourseLevel;
	}
	public String getFieldOfInterest() {
		return fieldOfInterest;
	}
	public void setFieldOfInterest(String fieldOfInterest) {
		this.fieldOfInterest = fieldOfInterest;
	}
	public String getDesiredCountries() {
		return desiredCountries;
	}
	public void setDesiredCountries(String desiredCountries) {
		this.desiredCountries = desiredCountries;
	}
	public String getPlanToGo() {
		return planToGo;
	}
	public void setPlanToGo(String planToGo) {
		this.planToGo = planToGo;
	}
	public String getSourceOfFunding() {
		return SourceOfFunding;
	}
	public void setSourceOfFunding(String sourceOfFunding) {
		SourceOfFunding = sourceOfFunding;
	}
	public String getGraduationStatus() {
		return graduationStatus;
	}
	public void setGraduationStatus(String graduationStatus) {
		this.graduationStatus = graduationStatus;
	}
	public String getGraduationCourse() {
		return graduationCourse;
	}
	public void setGraduationCourse(String graduationCourse) {
		this.graduationCourse = graduationCourse;
	}
	public Double getGraduationMarks() {
		return graduationMarks;
	}
	public void setGraduationMarks(Double graduationMarks) {
		this.graduationMarks = graduationMarks;
	}
	public String getGraduationMonth() {
		return graduationMonth;
	}
	public void setGraduationMonth(String graduationMonth) {
		this.graduationMonth = graduationMonth;
	}
	public Double getGraduationYear() {
		return graduationYear;
	}
	public void setGraduationYear(Double graduationYear) {
		this.graduationYear = graduationYear;
	}
	public String getStdXIIStream() {
		return stdXIIStream;
	}
	public void setStdXIIStream(String stdXIIStream) {
		this.stdXIIStream = stdXIIStream;
	}
	public Double getStdXIIMarks() {
		return stdXIIMarks;
	}
	public void setStdXIIMarks(Double stdXIIMarks) {
		this.stdXIIMarks = stdXIIMarks;
	}
	public Double getStdXIIYear() {
		return stdXIIYear;
	}
	public void setStdXIIYear(Double stdXIIYear) {
		this.stdXIIYear = stdXIIYear;
	}
	public String getExamTaken1() {
		return examTaken1;
	}
	public void setExamTaken1(String examTaken1) {
		this.examTaken1 = examTaken1;
	}
	public String getExamTaken2() {
		return examTaken2;
	}
	public void setExamTaken2(String examTaken2) {
		this.examTaken2 = examTaken2;
	}
	public String getExamTaken3() {
		return examTaken3;
	}
	public void setExamTaken3(String examTaken3) {
		this.examTaken3 = examTaken3;
	}
	public String getPreferredTimeToCall() {
		return preferredTimeToCall;
	}
	public void setPreferredTimeToCall(String preferredTimeToCall) {
		this.preferredTimeToCall = preferredTimeToCall;
	}
	public String getCurrentLocation() {
		return currentLocation;
	}
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}
	public String getUserComments() {
		return userComments;
	}
	public void setUserComments(String userComments) {
		this.userComments = userComments;
	}
	public String getDateOfRegistration() {
		return dateOfRegistration;
	}
	public void setDateOfRegistration(String dateOfRegistration) {
		this.dateOfRegistration = dateOfRegistration;
	}
	public String getIsInNDNCList() {
		return isInNDNCList;
	}
	public void setIsInNDNCList(String isInNDNCList) {
		this.isInNDNCList = isInNDNCList;
	}
	public Double getMobile() {
		return mobile;
	}
	public void setMobile(Double mobile) {
		this.mobile = mobile;
	}
	
	
	
}
