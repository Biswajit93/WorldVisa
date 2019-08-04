package com.worldvisa.model;

import java.util.Date;

public class PaymentCurrent {

	private String planAmount;
	private String chequeDdNo;
	private String paymentId;
	private String paymentMode;
	private Date transactionDt;
	private String invoiceSentInd;
	private String name;
	private String phoneNo;
	private String remarks;
	private String paymentPlan;
	private String paymentCurrentAmount;
	private String paymentTotalAmount;
	private String country;
	private String reportID;
	private String paymentPendingAmount;
	private String invoiceNo;

	public String getChequeDdNo() {
		return chequeDdNo;
	}

	public void setChequeDdNo(String chequeDdNo) {
		this.chequeDdNo = chequeDdNo;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Date getTransactionDt() {
		return transactionDt;
	}

	public void setTransactionDt(Date transactionDt) {
		this.transactionDt = transactionDt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getPaymentPlan() {
		return paymentPlan;
	}

	public void setPaymentPlan(String paymentPlan) {
		this.paymentPlan = paymentPlan;
	}

	public String getPaymentCurrentAmount() {
		return paymentCurrentAmount;
	}

	public void setPaymentCurrentAmount(String paymentCurrentAmount) {
		this.paymentCurrentAmount = paymentCurrentAmount;
	}

	public String getPaymentTotalAmount() {
		return paymentTotalAmount;
	}

	public void setPaymentTotalAmount(String paymentTotalAmount) {
		this.paymentTotalAmount = paymentTotalAmount;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getReportID() {
		return reportID;
	}

	public void setReportID(String reportID) {
		this.reportID = reportID;
	}

	public String getInvoiceSentInd() {
		return invoiceSentInd;
	}

	public void setInvoiceSentInd(String invoiceSentInd) {
		this.invoiceSentInd = invoiceSentInd;
	}

	public String getPlanAmount() {
		return planAmount;
	}

	public void setPlanAmount(String planAmount) {
		this.planAmount = planAmount;
	}

	public String getPaymentPendingAmount() {
		return paymentPendingAmount;
	}

	public void setPaymentPendingAmount(String paymentPendingAmount) {
		this.paymentPendingAmount = paymentPendingAmount;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

}
