package com.patientHub.main.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.patientHub.main.model.MedicalRecord;
import com.patientHub.main.repo.MedicalRecordRepo;

@Repository
public class MedicalRecordDaoImpl implements MedicalRecordDao {

	@Autowired
	private MedicalRecordRepo medicalRecordRepo;
	
	@Override
	public List<MedicalRecord> getAllMedicalRecords() {
		return medicalRecordRepo.findAll();
	}

	@Override
	public MedicalRecord getMedicalRecordById(Long medicalRecordId) {
		Optional<MedicalRecord> medicalRecordById = medicalRecordRepo.findById(medicalRecordId);
		if(medicalRecordById.isEmpty()) {
			return null;
		}else {
			return medicalRecordById.get();
		}	
	}

	@Override
	public String saveMedicalRecord(MedicalRecord medicalRecord) {		
		MedicalRecord savedMedicalRecord = medicalRecordRepo.save(medicalRecord);
		return savedMedicalRecord.getMedicalRecordId() + " ID medical record saved successfully";
	}

	@Override
	public String deleteMedicalRecord(Long medicalRecordId) {
		Optional<MedicalRecord> medicalRecordById = medicalRecordRepo.findById(medicalRecordId);
		if(medicalRecordById.isEmpty()) {
			return "Medical record not found";
		}else {
			MedicalRecord deleteMedicalRecord = medicalRecordById.get();
			medicalRecordRepo.delete(deleteMedicalRecord);
			return deleteMedicalRecord.getMedicalRecordId() + " ID medical record deleted successfully";			
		}	
	}

}
