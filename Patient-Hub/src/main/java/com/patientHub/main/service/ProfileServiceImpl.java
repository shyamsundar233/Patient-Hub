//$Id$
package com.patientHub.main.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService{

	@Autowired
	private ConfigurableApplicationContext configurableApplicationContext;
	
	@Override
	public String getCurrentProfile() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		List<Object> grantedAuth = Arrays.asList(authentication.getAuthorities().toArray());
		
		
		return grantedAuth != null ? grantedAuth.get(0).toString() : null;
	}
	
	@Override
	public boolean isAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null ? authentication.isAuthenticated() : Boolean.FALSE;
	}
	
	@Override
	public String updateProfile() {
		
		ConfigurableEnvironment environment = configurableApplicationContext.getEnvironment();
		String currentProfile = getCurrentProfile();
		if(!Arrays.asList(environment.getActiveProfiles()).contains(currentProfile)) {
			environment.setActiveProfiles(currentProfile);
	        configurableApplicationContext.refresh();
			return "Profile updated!!!";
		}
        return "Profile is already updated!!!";
	}

}
