//$Id$
package com.patientHub.main.Dao;

import java.util.List;

import com.patientHub.main.model.Userrole;

public interface UserroleDao {

	public List<Userrole> getAllRoles();
	
	public Userrole getRoleByUsername(String userName);
	
	public String saveUserRole(Userrole userRole);
	
	public String deleteUserRole(String userName);
	
}
