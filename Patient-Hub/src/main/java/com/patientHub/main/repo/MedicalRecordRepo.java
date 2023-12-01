package com.patientHub.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patientHub.main.model.MedicalRecord;

public interface MedicalRecordRepo extends JpaRepository<MedicalRecord, Long>{

}
