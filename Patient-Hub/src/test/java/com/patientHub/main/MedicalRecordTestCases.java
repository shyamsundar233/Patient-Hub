//$Id$
package com.patientHub.main;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.patientHub.main.exception.MedicalRecordNotFoundException;
import com.patientHub.main.model.MedicalRecord;
import com.patientHub.main.repo.MedicalRecordRepo;
import com.patientHub.main.service.MedicalRecordService;
import com.patientHub.main.service.PatientService;

@SpringBootTest
class MedicalRecordTestCases {
	
	@Autowired
    private MedicalRecordRepo medicalRecordRepo;

    @Autowired
    private MedicalRecordService medicalRecordService;
    
    @Autowired
    private PatientService patientService;
	
	@Test
	void testAllMedicalRec() {		
		List<MedicalRecord> medicalRecFromService = medicalRecordService.getAllMedicalRecords();
		List<MedicalRecord> medicalRecFromRepo = medicalRecordRepo.findAll();
		assertIterableEquals(medicalRecFromRepo, medicalRecFromService);		
	}
	
	@Test
	void testMedicalRecById() {		
		List<MedicalRecord> medicalRecsFromService = medicalRecordService.getAllMedicalRecords();
		Random random = new Random();
		int index = random.nextInt(medicalRecsFromService.size());
		MedicalRecord medicalRec = medicalRecsFromService.get(index);
		MedicalRecord medicalRecFromRepo = medicalRecordRepo.findById(medicalRec.getMedicalRecordId()).get();
		MedicalRecord medicalRecFromService = medicalRecordService.getMedicalRecordById(medicalRec.getMedicalRecordId());
		assertEquals(medicalRecFromRepo, medicalRecFromService);
	}
	
	@Test
	void testSaveMedicalRec() throws Exception {
		MedicalRecord medicalRec = new MedicalRecord();
		medicalRec.setPatientDiagnosis(RandomStringUtils.randomAlphabetic(6));
		medicalRec.setPatientTreatment(RandomStringUtils.randomAlphabetic(6));
		medicalRec.setRecordDate(LocalDate.now());
		medicalRec.setRecordNotes("Created During Testing");
		medicalRec.setPatient(patientService.getAllPatient().get(0));
		
		medicalRecordService.saveMedicalRecord(medicalRec);
		
		for(MedicalRecord medicalRecord : medicalRecordService.getAllMedicalRecords()) {
			if(medicalRecord.getPatientDiagnosis().equals(medicalRec.getPatientDiagnosis())) {
				assertEquals(medicalRecord, medicalRec);
			}
		}
	}
	
	@Test
	void testUpdateMedicalRec() throws Exception {
		MedicalRecord medicalRecFromService = medicalRecordService.getAllMedicalRecords().get(0);
		medicalRecFromService.setPatientTreatment(RandomStringUtils.randomAlphabetic(6));
		medicalRecordService.saveMedicalRecord(medicalRecFromService);
		MedicalRecord updatedMedicalRec = medicalRecordService.getMedicalRecordById(medicalRecFromService.getMedicalRecordId());
		assertEquals(updatedMedicalRec.getPatientTreatment(), medicalRecFromService.getPatientTreatment());
	}
	
	@Test
	void testDeleteMedicalRec() {
		MedicalRecord medicalRecFromService = medicalRecordService.getAllMedicalRecords().get(0);
		medicalRecordService.deleteMedicalRecord(medicalRecFromService.getMedicalRecordId());
		assertThrows(MedicalRecordNotFoundException.class, () -> {
            medicalRecordService.getMedicalRecordById(medicalRecFromService.getMedicalRecordId());
        });		
	}

}
