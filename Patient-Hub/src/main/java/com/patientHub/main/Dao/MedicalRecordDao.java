package com.patientHub.main.Dao;

import java.util.List;

import com.patientHub.main.model.MedicalRecord;

public interface MedicalRecordDao {
	
	public List<MedicalRecord> getAllMedicalRecords();
	
	public MedicalRecord getMedicalRecordById(Long medicalRecordId);
	
	public String saveMedicalRecord(MedicalRecord medicalRecord);
	
	public String deleteMedicalRecord(Long medicalRecordId);
	
	public List<MedicalRecord> getAllMedicalRecordsByPatientId(Long patientId);

}
