package com.worldvisa.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ContactFiles")
public class ContactFiles {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int contactsFileId;
	
	private String service;
	private String branch;
	private String dataGroup;
	private String dataSource;
	private String fileName;
	
	
	public int getContactsFileId() {
		return contactsFileId;
	}
	public void setContactsFileId(int contactsFileId) {
		this.contactsFileId = contactsFileId;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getDataGroup() {
		return dataGroup;
	}
	public void setDataGroup(String dataGroup) {
		this.dataGroup = dataGroup;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
}
