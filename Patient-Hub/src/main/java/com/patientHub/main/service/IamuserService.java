//$Id$
package com.patientHub.main.service;

import java.util.List;

import com.patientHub.main.model.Iamuser;

public interface IamuserService {
	
	public List<Iamuser> getAllIamUsers();
	
	public Iamuser getIamUser(String userName);
	
	public String saveIamUser(Iamuser iamUser);
	
	public String deleteIamUser(String userName);
}
