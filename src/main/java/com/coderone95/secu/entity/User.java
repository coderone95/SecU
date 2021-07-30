package com.coderone95.secu.entity;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Table(name ="tbl_user")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long user_id;

	@NotNull(message = "Name cannot be blanked")
	@NotBlank(message = "Name cannot be blanked")
	private String name;
	
	@Column(name="email_id")
	@NotBlank(message = "Email id cannot be blanked")
	@NotNull(message = "Email id cannot be blanked")
	@Email(message = "Invalid email")
	private String email_id;

	@NotNull(message = "Password cannot be blanked")
	@NotBlank(message = "Password cannot be blanked")
	private String password;

	private String phone;
	
	@Column(name="profile_pic_key_data")
	private String profile_pic_data;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SecCred> secCreds;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SecGeneralInfo> secGenInfo;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SecBankDetails> secBankDetails;

	private String secret_key;

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProfile_pic_data() {
		return profile_pic_data;
	}

	public void setProfile_pic_data(String profile_pic_data) {
		this.profile_pic_data = profile_pic_data;
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

	public String getSecret_key() {
		return secret_key;
	}

	public void setSecret_key(String secret_key) {
		this.secret_key = secret_key;
	}
}
