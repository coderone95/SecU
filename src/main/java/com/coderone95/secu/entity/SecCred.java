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
@Table(name="tbl_sec_cred")
public class SecCred {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="sec_cred_id")
	private Long secCredId;
//
//	@Column(name="user_id")
//	private Long userId;
	
	@Column(name="sec_name")
	private String secName;
	private String url;
	private String username;
	private String password;
	
	@Column(name="other_data")
	private String otherData;
	
	@ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

	public Long getSecCredId() {
		return secCredId;
	}

	public void setSecCredId(Long secCredId) {
		this.secCredId = secCredId;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
	
	
}
