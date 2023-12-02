//$Id$
package com.patientHub.main;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.patientHub.main.exception.PatientNotFoundException;
import com.patientHub.main.model.Patient;
import com.patientHub.main.repo.PatientRepo;
import com.patientHub.main.service.PatientService;

@SpringBootTest
class PatientTestCases {
	
	@Autowired
    private PatientRepo patientRepo;

    @Autowired
    private PatientService patientService;
	
	@Test
	void testAllPatients() {		
		List<Patient> patientFromService = patientService.getAllPatient();
		List<Patient> patientFromRepo = patientRepo.findAll();
		assertIterableEquals(patientFromRepo, patientFromService);		
	}
	
	@Test
	void testPatientById() {		
		List<Patient> patientsFromService = patientService.getAllPatient();
		Random random = new Random();
		int index = random.nextInt(patientsFromService.size());
		Patient patient = patientsFromService.get(index);
		Patient patientFromRepo = patientRepo.findById(patient.getPatientId()).get();
		Patient patientFromService = patientService.getPatientById(patient.getPatientId());
		assertEquals(patientFromRepo, patientFromService);
	}
	
	@Test
	void testSavePatient() throws Exception {
		Patient patient = new Patient();
		patient.setPatientName(RandomStringUtils.randomAlphabetic(6));
		patient.setPatientAge(Integer.parseInt(RandomStringUtils.randomNumeric(2)));
		patient.setPatientGender("Male");
		patient.setPatientType("IN");
		patientService.savePatient(patient);
		
		for(Patient patientItr : patientService.getAllPatient()) {
			if(patientItr.getPatientName().equals(patient.getPatientName())) {
				assertEquals(patientItr, patient);
			}
		}
	}
	
	@Test
	void testUpdatePatient() throws Exception {
		Patient patientFromService = patientService.getAllPatient().get(0);
		patientFromService.setPatientName("Patient JUNIT");
		patientService.savePatient(patientFromService);
		Patient updatedPatient = patientService.getPatientById(patientFromService.getPatientId());
		assertEquals(updatedPatient.getPatientName(), "Patient JUNIT");
	}
	
	@Test
	void testDeletePatient() {
		Patient patientFromService = patientService.getAllPatient().get(0);
		patientService.deletePatient(patientFromService.getPatientId());
		assertThrows(PatientNotFoundException.class, () -> {
            patientService.getPatientById(patientFromService.getPatientId());
        });		
	}

}
