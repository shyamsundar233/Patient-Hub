package com.patientHub.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patientHub.main.Dao.MedicalRecordDao;
import com.patientHub.main.model.MedicalRecord;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

	@Autowired
	private MedicalRecordDao medicalRecordDao;
	
	@Override
	public List<MedicalRecord> getAllMedicalRecords() {
		return medicalRecordDao.getAllMedicalRecords();
	}

	@Override
	public MedicalRecord getMedicalRecordById(Long medicalRecordId) {
		return medicalRecordDao.getMedicalRecordById(medicalRecordId);
	}

	@Override
	public String saveMedicalRecord(MedicalRecord medicalRecord) {
		return medicalRecordDao.saveMedicalRecord(medicalRecord);
	}

	@Override
	public String deleteMedicalRecord(Long medicalRecordId) {
		return medicalRecordDao.deleteMedicalRecord(medicalRecordId);
	}

}
