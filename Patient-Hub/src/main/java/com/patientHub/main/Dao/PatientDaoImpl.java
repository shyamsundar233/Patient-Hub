package com.patientHub.main.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.patientHub.main.model.Patient;
import com.patientHub.main.repo.PatientRepo;

@Repository
public class PatientDaoImpl implements PatientDao {
	
	@Autowired
	private PatientRepo patientRepo;

	@Override
	public List<Patient> getAllPatient() {		
		return patientRepo.findAll();
	}

	@Override
	public Patient getPatientById(Long patientId) {
		Optional<Patient> patientById = patientRepo.findById(patientId);
		if(patientById.isEmpty()) {
			return null;
		}else {
			return patientById.get();
		}		
	}

	@Override
	public String savePatient(Patient patient) {
		Patient savedPatient = patientRepo.save(patient);
		return savedPatient.getPatientName() + " patient saved successfully";
	}

	@Override
	public String deletePatient(Long patientId) {
		Optional<Patient> patientById = patientRepo.findById(patientId);
		if(patientById.isEmpty()) {
			return "Patient not found";
		}else {
			Patient deletePatient = patientById.get();
			patientRepo.delete(deletePatient);
			return deletePatient.getPatientName() + " patient deleted successfully";			
		}		
	}

}
