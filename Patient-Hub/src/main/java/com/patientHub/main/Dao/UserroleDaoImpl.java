//$Id$
package com.patientHub.main.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.patientHub.main.model.Userrole;
import com.patientHub.main.repo.UserroleRepo;

import jakarta.ws.rs.NotFoundException;

@Repository
public class UserroleDaoImpl implements UserroleDao {
	
	@Autowired
	private UserroleRepo userroleRepo;

	@Override
	public List<Userrole> getAllRoles() {
		return userroleRepo.findAll();
	}

	@Override
	public Userrole getRoleByUsername(String userName) {
		Optional<Userrole> userRoleByName = userroleRepo.findById(userName);
		if(userRoleByName.isEmpty()) {
			throw new NotFoundException("No users found");
		}
		return userRoleByName.get();
	}

	@Override
	public String saveUserRole(Userrole userRole) {
		userroleRepo.save(userRole);
		return "User role saved successfully";
	}

	@Override
	public String deleteUserRole(String userName) {
		userroleRepo.deleteById(userName);
		return "User role deleted successfully";
	}

}
