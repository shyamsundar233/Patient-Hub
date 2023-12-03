//$Id$
package com.patientHub.main.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Iamuser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public String userName;
	
	public String userPassword;
	
	public int userStatus;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	@Override
	public String toString() {
		return "Iamuser [userName=" + userName + ", userPassword=" + userPassword + ", userStatus=" + userStatus + "]";
	}

}
