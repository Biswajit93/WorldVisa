package com.worldvisa.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Notification")
public class Notification {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int notificationId;
	
	private Timestamp sentWhen;
	
	private String fromWhom;
	
	private String toWhom;
	
	private String subject;
	
	
	
	public int getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}
	
	
	public String getToWhom() {
		return toWhom;
	}
	public void setToWhom(String toWhom) {
		this.toWhom = toWhom;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getFromWhom() {
		return fromWhom;
	}
	public void setFromWhom(String fromWhom) {
		this.fromWhom = fromWhom;
	}
	public Timestamp getSentWhen() {
		return sentWhen;
	}
	public void setSentWhen(Timestamp sentWhen) {
		this.sentWhen = sentWhen;
	}
	

}
