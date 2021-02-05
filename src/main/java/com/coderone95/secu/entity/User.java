package com.coderone95.secu.entity;

import java.util.List;

import javax.persistence.*;


@Entity
@Table(name ="tbl_user")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_id")
	private Long userId;
	
	private String name;
	
	@Column(name="email_id")
	private String emailId;
	
	private String password;
	
	private String phone;
	
	@Column(name="profile_pic_key_data")
	private String profilePicData;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SecCred> secCreds;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SecGeneralInfo> secGenInfo;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SecBankDetails> secBankDetails;


	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getProfilePicData() {
		return profilePicData;
	}
	public void setProfilePicData(String profilePicData) {
		this.profilePicData = profilePicData;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<SecCred> getSecCreds() {
		return secCreds;
	}
	public void setSecCreds(List<SecCred> secCreds) {
		this.secCreds = secCreds;
	}
	public List<SecGeneralInfo> getSecGenInfo() {
		return secGenInfo;
	}
	public void setSecGenInfo(List<SecGeneralInfo> secGenInfo) {
		this.secGenInfo = secGenInfo;
	}
	public List<SecBankDetails> getSecBankDetails() {
		return secBankDetails;
	}
	public void setSecBankDetails(List<SecBankDetails> secBankDetails) {
		this.secBankDetails = secBankDetails;
	}


}
