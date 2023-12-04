//$Id$
package com.patientHub.main.service;

import java.util.List;

import com.patientHub.main.model.Userrole;

public interface UserroleService {
	
	public List<Userrole> getAllRoles();
	
	public Userrole getRoleByUsername(String userName);
	
	public String saveUserRole(Userrole userRole);
	
	public String deleteUserRole(String userName);
}
