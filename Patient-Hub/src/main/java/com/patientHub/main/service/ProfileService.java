//$Id$
package com.patientHub.main.service;

public interface ProfileService {
	
	public String getCurrentProfile();
	
	public String updateProfile();
	
	public boolean isAuthenticated();
}
