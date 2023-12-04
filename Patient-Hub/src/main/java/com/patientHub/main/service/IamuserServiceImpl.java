//$Id$
package com.patientHub.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patientHub.main.Dao.IamuserDao;
import com.patientHub.main.model.Iamuser;

@Service
public class IamuserServiceImpl implements IamuserService {
	
	@Autowired
	private IamuserDao iamuserDao;

	@Override
	public List<Iamuser> getAllIamUsers() {
		return iamuserDao.getAllIamUsers();
	}

	@Override
	public Iamuser getIamUser(String userName) {
		return iamuserDao.getIamUser(userName);
	}

	@Override
	public String saveIamUser(Iamuser iamUser) {
		return iamuserDao.saveIamUser(iamUser);
	}

	@Override
	public String deleteIamUser(String userName) {
		return iamuserDao.deleteIamUser(userName);
	}

}
