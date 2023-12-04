//$Id$
package com.patientHub.main.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.patientHub.main.model.Iamuser;
import com.patientHub.main.repo.IamuserRepo;

import jakarta.ws.rs.NotFoundException;

@Repository
public class IamuserDaoImpl implements IamuserDao{
	
	@Autowired
	private IamuserRepo iamuserRepo;

	@Override
	public List<Iamuser> getAllIamUsers() {
		return iamuserRepo.findAll();
	}

	@Override
	public Iamuser getIamUser(String userName) {
		Optional<Iamuser> iamUserByName = iamuserRepo.findById(userName);
		if(iamUserByName.isEmpty()) {
			throw new NotFoundException("No users found");
		}
		return iamUserByName.get();
	}

	@Override
	public String saveIamUser(Iamuser iamUser) {
		iamuserRepo.save(iamUser);
		return "User saved successfully";
	}

	@Override
	public String deleteIamUser(String userName) {
		iamuserRepo.deleteById(userName);
		return "User deleted successfully";
	}

	

}
