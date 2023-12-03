//$Id$
package com.patientHub.main.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Userrole {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public String userName;
	
	public String userRole;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	@Override
	public String toString() {
		return "Userrole [userName=" + userName + ", userRole=" + userRole + "]";
	}

}
