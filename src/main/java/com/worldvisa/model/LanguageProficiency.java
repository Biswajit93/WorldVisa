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
@Table(name = "LanguageProficiency")
public class LanguageProficiency {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int languageProficiencyID;
	
	public String Language;
	
	public String SpeakingLevel;
	
	public String ReadingLevel;
	
	public String WritingLevel;
	
	public String Interpretation;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reportId",  nullable = false)
    private UserDetails userDetails;


	public String getLanguage() {
		return Language;
	}

	public void setLanguage(String language) {
		Language = language;
	}

	public String getSpeakingLevel() {
		return SpeakingLevel;
	}

	public void setSpeakingLevel(String speakingLevel) {
		SpeakingLevel = speakingLevel;
	}

	public String getReadingLevel() {
		return ReadingLevel;
	}

	public void setReadingLevel(String readingLevel) {
		ReadingLevel = readingLevel;
	}

	public String getWritingLevel() {
		return WritingLevel;
	}

	public void setWritingLevel(String writingLevel) {
		WritingLevel = writingLevel;
	}

	public String getInterpretation() {
		return Interpretation;
	}

	public void setInterpretation(String interpretation) {
		Interpretation = interpretation;
	}

	public int getLanguageProficiencyID() {
		return languageProficiencyID;
	}

	public void setLanguageProficiencyID(int languageProficiencyID) {
		this.languageProficiencyID = languageProficiencyID;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

}
