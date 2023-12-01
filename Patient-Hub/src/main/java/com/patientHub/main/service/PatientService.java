package com.patientHub.main.service;

import java.util.List;

import com.patientHub.main.exception.PatientInvalidDataException;
import com.patientHub.main.exception.PatientMandatoryFieldNotFoundException;
import com.patientHub.main.model.Patient;

public interface PatientService {
	
	public List<Patient> getAllPatient();
	
	public Patient getPatientById(Long patientId);
	
	public String savePatient(Patient patient) throws PatientMandatoryFieldNotFoundException, PatientInvalidDataException;
	
	public String deletePatient(Long patientId);
}
