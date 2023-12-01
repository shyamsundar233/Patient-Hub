package com.patientHub.main.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patientHub.main.exception.PatientInvalidDataException;
import com.patientHub.main.exception.PatientMandatoryFieldNotFoundException;
import com.patientHub.main.model.Patient;
import com.patientHub.main.service.PatientService;

@RestController
@RequestMapping("/api/v1")
@SuppressWarnings("unchecked")
public class PatientController {
	
	@Autowired
	private PatientService patientService;
	
	
	@GetMapping("/patient")
	public JSONObject getAllPatient() {
		JSONObject allPatients = new JSONObject();
		
		allPatients.put("Patients", patientService.getAllPatient());
		
		return allPatients;
	}
	
	@PostMapping("/patient/{patientId}")
	public JSONObject getPatientById(@PathVariable Long patientId) {
		JSONObject response = new JSONObject();
		Patient patient = patientService.getPatientById(patientId);
		if(patient != null) {
			response.put("Patient", patient);
		}else {
			response.put("Patient", "Invalid Patient Id");
		}		
		return response;
	}
	
	@PostMapping("/patient")
	public JSONObject savePatient(@RequestBody Patient patient) throws PatientMandatoryFieldNotFoundException, PatientInvalidDataException {
		JSONObject response = new JSONObject();		
		
		response.put("Patient", patientService.savePatient(patient));
		return response;
	}
	
	@DeleteMapping("/patient/{patientId}")
	public JSONObject deletePatient(@PathVariable Long patientId) {
		JSONObject response = new JSONObject();
		
		response.put("Patient", patientService.deletePatient(patientId));
		
		return response;
	}
	

}
