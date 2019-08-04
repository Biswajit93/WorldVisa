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
@Table(name = "PaymentTransaction")
public class PaymentTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int paymentTransactionID;

	public String ID;

	public String Mode;

	public String Amount;

	public String Remarks;

	public String ChequeorDDNumber;

	@Temporal(TemporalType.TIMESTAMP)
	public Date TransactionDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reportId", nullable = false)
	private UserDetails userDetails;

	public int getPaymentTransactionID() {
		return paymentTransactionID;
	}

	public void setPaymentTransactionID(int paymentTransactionID) {
		this.paymentTransactionID = paymentTransactionID;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getMode() {
		return Mode;
	}

	public void setMode(String mode) {
		Mode = mode;
	}

	public String getAmount() {
		return Amount;
	}

	public void setAmount(String amount) {
		Amount = amount;
	}

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		Remarks = remarks;
	}

	public String getChequeorDDNumber() {
		return ChequeorDDNumber;
	}

	public void setChequeorDDNumber(String ChequeorDDNumber) {
		this.ChequeorDDNumber = ChequeorDDNumber;
	}

	public Date getTransactionDate() {
		return TransactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		TransactionDate = transactionDate;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

}
