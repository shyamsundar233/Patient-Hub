package com.patientHub.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patientHub.main.Dao.PatientDao;
import com.patientHub.main.model.Patient;

@Service
public class PatientServiceImpl implements PatientService {
	
	@Autowired
	private PatientDao patientDao;

	@Override
	public List<Patient> getAllPatient() {
		return patientDao.getAllPatient();
	}

	@Override
	public Patient getPatientById(Long patientId) {
		return patientDao.getPatientById(patientId);
	}

	@Override
	public String savePatient(Patient patient) {
		return patientDao.savePatient(patient);
	}

	@Override
	public String deletePatient(Long patientId) {
		return patientDao.deletePatient(patientId);
	}

}
