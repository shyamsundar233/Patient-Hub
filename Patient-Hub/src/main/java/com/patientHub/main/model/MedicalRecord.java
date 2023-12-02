package com.patientHub.main.model;

import java.time.LocalDate;
import java.util.Objects;

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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        MedicalRecord record = (MedicalRecord) obj;

        return Objects.equals(medicalRecordId, record.medicalRecordId)
                && Objects.equals(patientDiagnosis, record.patientDiagnosis)
                && Objects.equals(patientTreatment, record.patientTreatment)
                && Objects.equals(recordDate, record.recordDate)
                && Objects.equals(recordNotes, record.recordNotes)
                && Objects.equals(patient, record.patient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicalRecordId, patientDiagnosis, patientTreatment, recordDate, recordNotes, patient);
    }

	@Override
	public String toString() {
		return "MedicalRecord [medicalRecordId=" + medicalRecordId + ", patientDiagnosis=" + patientDiagnosis
				+ ", patientTreatment=" + patientTreatment + ", recordDate=" + recordDate + ", recordNotes="
				+ recordNotes + "]";
	}

}
