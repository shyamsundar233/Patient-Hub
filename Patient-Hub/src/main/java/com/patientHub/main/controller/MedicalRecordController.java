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

import com.patientHub.main.model.MedicalRecord;
import com.patientHub.main.service.MedicalRecordService;

@RestController
@RequestMapping("/api/v1")
@SuppressWarnings("unchecked")
public class MedicalRecordController {
	
	@Autowired
	private MedicalRecordService medicalRecordService;
	
	@GetMapping("/medicalRecord")
	public JSONObject getAllMedicalRecord() {
		JSONObject allMedicalRecords = new JSONObject();
		
		allMedicalRecords.put("Medical Records", medicalRecordService.getAllMedicalRecords());
		
		return allMedicalRecords;
	}
	
	@PostMapping("/medicalRecord/{medicalRecordId}")
	public JSONObject getMedicalRecordById(@PathVariable Long medicalRecordId) {
		JSONObject response = new JSONObject();
		MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordById(medicalRecordId);
		if(medicalRecord != null) {
			response.put("Medical Record", medicalRecord);
		}else {
			response.put("Medical Record", "Invalid Patient Id");
		}		
		return response;
	}
	
	@PostMapping("/medicalRecord")
	public JSONObject saveMedicalRecord(@RequestBody MedicalRecord medicalRecord) throws Exception {
		JSONObject response = new JSONObject();		
		
		response.put("Medical Record", medicalRecordService.saveMedicalRecord(medicalRecord));
		return response;
	}
	
	@DeleteMapping("/medicalRecord/{medicalRecordId}")
	public JSONObject deleteMedicalRecord(@PathVariable Long medicalRecordId) {
		JSONObject response = new JSONObject();
		
		response.put("Medical Record", medicalRecordService.deleteMedicalRecord(medicalRecordId));
		
		return response;
	}

}
