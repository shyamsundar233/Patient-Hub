//$Id$
package com.patientHub.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patientHub.main.Dao.UserroleDao;
import com.patientHub.main.model.Userrole;

@Service
public class UserroleServiceImpl implements UserroleService {
	
	@Autowired
	private UserroleDao userroleDao;

	@Override
	public List<Userrole> getAllRoles() {
		return userroleDao.getAllRoles();
	}

	@Override
	public Userrole getRoleByUsername(String userName) {
		return userroleDao.getRoleByUsername(userName);
	}

	@Override
	public String saveUserRole(Userrole userRole) {
		return userroleDao.saveUserRole(userRole);
	}

	@Override
	public String deleteUserRole(String userName) {
		return userroleDao.deleteUserRole(userName);
	}

}
