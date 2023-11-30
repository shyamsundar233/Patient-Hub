package com.patientHub.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patientHub.main.model.Patient;

public interface PatientRepo extends JpaRepository<Patient, Long>{

}
