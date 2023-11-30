package com.patientHub.main.Dao;

import java.util.List;

import com.patientHub.main.model.Patient;

public interface PatientDao{
	
	public List<Patient> getAllPatient();
	
	public Patient getPatientById(Long patientId);
	
	public String savePatient(Patient patient);
	
	public String deletePatient(Long patientId);

}
