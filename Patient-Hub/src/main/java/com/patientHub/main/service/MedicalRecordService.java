package com.patientHub.main.service;

import java.util.List;

import com.patientHub.main.model.MedicalRecord;

public interface MedicalRecordService {
	
	public List<MedicalRecord> getAllMedicalRecords();
	
	public MedicalRecord getMedicalRecordById(Long medicalRecordId);
	
	public String saveMedicalRecord(MedicalRecord medicalRecord) throws Exception;
	
	public String deleteMedicalRecord(Long medicalRecordId);

}
