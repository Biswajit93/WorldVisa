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

@Entity
@Table(name = "UserNotification")
public class UserNotification {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int notificationId;

	private Date whenSet;

	private Date dateSet;

	private String byWhom;

	private String status;

	private String subject;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reportId", nullable = false)
	private UserDetails userDetails;

	public int getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getWhenSet() {
		return whenSet;
	}

	public void setWhenSet(Date whenSet) {
		this.whenSet = whenSet;
	}

	public Date getDateSet() {
		return dateSet;
	}

	public void setDateSet(Date dateSet) {
		this.dateSet = dateSet;
	}

	public String getByWhom() {
		return byWhom;
	}

	public void setByWhom(String byWhom) {
		this.byWhom = byWhom;
	}

}
