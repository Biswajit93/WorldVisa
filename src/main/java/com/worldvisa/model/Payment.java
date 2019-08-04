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
@Table(name = "Payment")
public class Payment {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String numberOfVisa;
	
	private String additionalServices;
	
	private String paymentOption;
	
	private String costOfService;
	
	private String tax;
	
	private String netPayable;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reportId",  nullable = false)
    private UserDetails userDetails;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumberOfVisa() {
		return numberOfVisa;
	}

	public void setNumberOfVisa(String numberOfVisa) {
		this.numberOfVisa = numberOfVisa;
	}

	public String getAdditionalServices() {
		return additionalServices;
	}

	public void setAdditionalServices(String additionalServices) {
		this.additionalServices = additionalServices;
	}

	public String getPaymentOption() {
		return paymentOption;
	}

	public void setPaymentOption(String paymentOption) {
		this.paymentOption = paymentOption;
	}

	public String getCostOfService() {
		return costOfService;
	}

	public void setCostOfService(String costOfService) {
		this.costOfService = costOfService;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getNetPayable() {
		return netPayable;
	}

	public void setNetPayable(String netPayable) {
		this.netPayable = netPayable;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

}
