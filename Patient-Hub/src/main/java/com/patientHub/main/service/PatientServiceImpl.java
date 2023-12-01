package com.patientHub.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.patientHub.main.Dao.PatientDao;
import com.patientHub.main.exception.PatientInvalidDataException;
import com.patientHub.main.exception.PatientMandatoryFieldNotFoundException;
import com.patientHub.main.exception.PatientNotFoundException;
import com.patientHub.main.model.Patient;

@Service
public class PatientServiceImpl implements PatientService {
	
	@Autowired
	private PatientDao patientDao;
	
	@Value("${patient.gender}")
	private List<String> patientGenderList;
	
	@Value("${patient.type}")
	private List<String> patientTypeList;

	@Override
	public List<Patient> getAllPatient() {
		return patientDao.getAllPatient();
	}

	@Override
	public Patient getPatientById(Long patientId) {
		Patient patient = patientDao.getPatientById(patientId);
		if(patient == null) {
			throw new PatientNotFoundException("Patient not found");
		}else {
			return patient;
		}					
	}

	@Override
	public String savePatient(Patient patient) throws PatientMandatoryFieldNotFoundException, PatientInvalidDataException {
		if(patient.getPatientName() == null || patient.getPatientGender() == null || patient.getPatientAge() == 0 || patient.getPatientType() == null) {
			throw new PatientMandatoryFieldNotFoundException("Mandatory Field Not Found");
		}
		if(patient.getPatientName().chars().anyMatch(Character::isDigit)){
			throw new PatientInvalidDataException("Numbers are not allowed in patient name");
		}		
		if(!patientGenderList.contains(patient.getPatientGender())) {
			throw new PatientInvalidDataException("Invalid patient gender");
		}		
		if(!patientTypeList.contains(patient.getPatientType())) {
			throw new PatientInvalidDataException("Invalid patient type");
		}
		return patientDao.savePatient(patient);
	}

	@Override
	public String deletePatient(Long patientId) {
		return patientDao.deletePatient(patientId);
	}

}
