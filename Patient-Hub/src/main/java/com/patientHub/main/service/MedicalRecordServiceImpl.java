package com.patientHub.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.patientHub.main.Dao.MedicalRecordDao;
import com.patientHub.main.exception.MedicalRecInvalidDataException;
import com.patientHub.main.exception.MedicalRecMandatoryFieldNotFoundException;
import com.patientHub.main.exception.MedicalRecordNotFoundException;
import com.patientHub.main.model.MedicalRecord;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

	@Autowired
	private MedicalRecordDao medicalRecordDao;
	
	@Autowired
	private CacheManager cacheManager;
	
	@Override
	@Cacheable(value = "allMedicalRec")
	public List<MedicalRecord> getAllMedicalRecords() {
		return medicalRecordDao.getAllMedicalRecords();
	}

	@Override
	@Cacheable(value = "medicalRec")
	public MedicalRecord getMedicalRecordById(Long medicalRecordId) {
		
		MedicalRecord medicalRecord = medicalRecordDao.getMedicalRecordById(medicalRecordId);
		
		if(medicalRecord == null) {
			throw new MedicalRecordNotFoundException("Medical Record Not Found");
		}else {
			return medicalRecord;
		}
	}

	@Override
	public String saveMedicalRecord(MedicalRecord medicalRecord) throws Exception {
		validateMedicalRecord(medicalRecord);	
		String message = medicalRecordDao.saveMedicalRecord(medicalRecord);
		evictCache();
		return message;
	}

	@Override
	public String deleteMedicalRecord(Long medicalRecordId) {
		String message = medicalRecordDao.deleteMedicalRecord(medicalRecordId);
		evictCache();
		return message;
	}
	
	private void validateMedicalRecord(MedicalRecord medicalRecord) throws Exception {
		
		if(medicalRecord.getPatientDiagnosis() == null || medicalRecord.getPatientTreatment() == null || medicalRecord.getRecordDate() == null || medicalRecord.getPatient().getPatientId() == null){		
			throw new MedicalRecMandatoryFieldNotFoundException("Mandatory Field Not Found");
		}
		if(medicalRecord.getPatientDiagnosis().chars().anyMatch(Character::isDigit)) {
			throw new MedicalRecInvalidDataException("Numbers are not allowed in Diagnosis field");
		}
		if(medicalRecord.getPatientTreatment().chars().anyMatch(Character::isDigit)) {
			throw new MedicalRecInvalidDataException("Numbers are not allowed in treatment field");
		}		
	}
	
	@CacheEvict(value = "allMedicalRec", allEntries = true)
	public void evictCache() {
		cacheManager.getCache("allMedicalRec").clear();		
		cacheManager.getCache("medicalRec").clear();		
	}

}
