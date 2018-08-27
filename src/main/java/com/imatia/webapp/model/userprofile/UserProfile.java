package com.imatia.webapp.model.userprofile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class UserProfile {
	private Long usrId;
	private String userName;
	private String encryptedPassword;
	
	public UserProfile(){
		
	}
	
	public UserProfile(String userName, String encryptedPassword){
		
		this.userName = userName;
		this.encryptedPassword = encryptedPassword;
	}

	
	@Column(name = "usrId")
	@SequenceGenerator( 
	name = "UserProfileIdGenerator", 
	sequenceName = "UserProfileSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "UserProfileIdGenerator")
	public Long getUsrId() {
		return usrId;
	}

	public void setUsrId(Long usrId) {
		this.usrId = usrId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name = "enPassword")
	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	
}
