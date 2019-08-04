package com.worldvisa.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SocialFollowUp")
public class SocialFollowUp {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int followUpId;
	private String contactName;
	private String message;
	
	public int getFollowUpId() {
		return followUpId;
	}
	public void setFollowUpId(int followUpId) {
		this.followUpId = followUpId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
}
