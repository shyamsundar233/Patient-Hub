package com.patientHub.main.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class MedicalRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long medicalRecordId;
	
	private String patientDiagnosis;
	
	private String patientTreatment;
	
	private LocalDate recordDate;
	
	private String recordNotes;
	
	@ManyToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;

	public Long getMedicalRecordId() {
		return medicalRecordId;
	}

	public void setMedicalRecordId(Long medicalRecordId) {
		this.medicalRecordId = medicalRecordId;
	}

	public String getPatientDiagnosis() {
		return patientDiagnosis;
	}

	public void setPatientDiagnosis(String patientDiagnosis) {
		this.patientDiagnosis = patientDiagnosis;
	}

	public String getPatientTreatment() {
		return patientTreatment;
	}

	public void setPatientTreatment(String patientTreatment) {
		this.patientTreatment = patientTreatment;
	}

	public LocalDate getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(LocalDate recordDate) {
		this.recordDate = recordDate;
	}

	public String getRecordNotes() {
		return recordNotes;
	}

	public void setRecordNotes(String recordNotes) {
		this.recordNotes = recordNotes;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@Override
	public String toString() {
		return "MedicalRecord [medicalRecordId=" + medicalRecordId + ", patientDiagnosis=" + patientDiagnosis
				+ ", patientTreatment=" + patientTreatment + ", recordDate=" + recordDate + ", recordNotes="
				+ recordNotes + "]";
	}

}
