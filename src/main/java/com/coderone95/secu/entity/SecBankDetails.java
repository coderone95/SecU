package com.coderone95.secu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="tbl_sec_bank_details")
public class SecBankDetails {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="sec_bk_id")
	private Long secBKId;
	
	
//	@Column(name="user_id")
//	private Long userId;
//
	@Column(name="sec_name")
	private String secName;
	
	@Column(name="ifsc_code")
	private String ifscCode;
	
	@Column(name="account_number")
	private String accountNumber;
	
	@Column(name="branch_name")
	private String branch;
	
	@Column(name="icmr_code")
	private String icmrCode;
	
	@Column(name="banking_url")
	private String bankingURL;
	
	@Column(name="net_banking_username")
	private String username;
	
	@Column(name="net_banking_password")
	private String password;
	
	@Column(name="other_data")
	private String otherData;
	
	@ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

	@Column(name = "account_holder")
	private String accountHolder;

	public Long getSecBKId() {
		return secBKId;
	}

	public void setSecBKId(Long secBKId) {
		this.secBKId = secBKId;
	}

//	public Long getUserId() {
//		return userId;
//	}
//
//	public void setUserId(Long userId) {
//		this.userId = userId;
//	}

	public String getSecName() {
		return secName;
	}

	public void setSecName(String secName) {
		this.secName = secName;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getIcmrCode() {
		return icmrCode;
	}

	public void setIcmrCode(String icmrCode) {
		this.icmrCode = icmrCode;
	}

	public String getBankingURL() {
		return bankingURL;
	}

	public void setBankingURL(String bankingURL) {
		this.bankingURL = bankingURL;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOtherData() {
		return otherData;
	}

	public void setOtherData(String otherData) {
		this.otherData = otherData;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}
}
