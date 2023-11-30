package com.patientHub.main.service;

import java.util.List;

import com.patientHub.main.model.Patient;

public interface PatientService {
	
	public List<Patient> getAllPatient();
	
	public Patient getPatientById(Long patientId);
	
	public String savePatient(Patient patient);
	
	public String deletePatient(Long patientId);
}
