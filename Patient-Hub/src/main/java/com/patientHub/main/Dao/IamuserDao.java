//$Id$
package com.patientHub.main.Dao;

import java.util.List;

import com.patientHub.main.model.Iamuser;

public interface IamuserDao{
	
	public List<Iamuser> getAllIamUsers();
	
	public Iamuser getIamUser(String userName);
	
	public String saveIamUser(Iamuser iamUser);
	
	public String deleteIamUser(String userName);
	
}
