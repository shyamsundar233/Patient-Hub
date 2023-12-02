package com.patientHub.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.patientHub.main.model.MedicalRecord;
import com.patientHub.main.model.Patient;
import com.patientHub.main.service.MedicalRecordService;
import com.patientHub.main.service.PatientService;

@Controller
public class MainController {
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private MedicalRecordService medicalRecordService;
	
	@Value("${patient.gender}")
	private List<String> genderList;
	
	@Value("${patient.type}")
	private List<String> typesList;
	
	@GetMapping("/")
	public String index(Authentication authentication, Model theModel) {
		return "index";
	}
	
	@GetMapping("/showPatients")
	public String showPatients(Model theModel) {		
		theModel.addAttribute("patientsList", patientService.getAllPatient());
		return "view/patients-list";
	}
	
	@GetMapping("/showPatientDetails")
	public String showPatientDetails(@RequestParam(name = "patientId") Long patientId, Model theModel){
		theModel.addAttribute("patient", patientService.getPatientById(patientId));
		return "view/patient-details";
	}
	
	@GetMapping("/showAddPatient")
	public String showAddPatientForm(Model theModel) {
		theModel.addAttribute("patient", new Patient());
		theModel.addAttribute("genderList", genderList);
		theModel.addAttribute("typesList", typesList);
		return "view/add-patient";
	}
	
	@PostMapping("/addPatient")
	public String addPatient(@ModelAttribute Patient patient, Model theModel) throws Exception {
		patientService.savePatient(patient);
		return "redirect:/showPatients";
	}
	
	@GetMapping("/editPatient")
	public String editPatient(@RequestParam(name = "patientId") Long patientId, Model theModel){
		theModel.addAttribute("patient", patientService.getPatientById(patientId));
		theModel.addAttribute("genderList", genderList);
		theModel.addAttribute("typesList", typesList);
		return "view/add-patient";
	}
	
	@GetMapping("/deletePatient")
	public String deletePatient(@RequestParam(name = "patientId") Long patientId){
		patientService.deletePatient(patientId);
		return "redirect:/showPatients";
	}
	
	@GetMapping("/showAllMedicalRec")
	public String showAllMedicalRec(Model theModel) {
		List<MedicalRecord> medicalRecList = medicalRecordService.getAllMedicalRecords();
		theModel.addAttribute("medicalRecordList", medicalRecList);
		return "view/medical-rec";
	}
	
	@GetMapping("/showMedicalRec")
	public String showMedicalRecForPatient(@RequestParam(name = "patientId") Long patientId, Model theModel) {
		List<MedicalRecord> medicalRec = medicalRecordService.getAllMedicalRecordsByPatientId(patientId);
		theModel.addAttribute("medicalRecordList", medicalRec);
		return "view/medical-rec";
	}
	
	@GetMapping("/showAddMedicalRec")
	public String showAddMedicalRec(@RequestParam(name = "patientId") Long patientId, Model theModel) {
		MedicalRecord medicalRecord = new MedicalRecord();
		Patient patientById = patientService.getPatientById(patientId);
		medicalRecord.setPatient(patientById);
		theModel.addAttribute("medicalRecord", medicalRecord);
		return "/view/add-medical-rec";
	}
	
	@PostMapping("/addMedicalRecord")
	public String addMedicalRecord(@ModelAttribute MedicalRecord medicalRecord, Model theModel) throws Exception {
		medicalRecordService.saveMedicalRecord(medicalRecord);
		return "redirect:/showMedicalRec?patientId=" + medicalRecord.getPatient().getPatientId();
	}
	
	@GetMapping("/editMedicalRecord")
	public String editMedicalRecord(@RequestParam(name = "medicalRecordId") Long medicalRecordId, Model theModel){
		MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordById(medicalRecordId);
		theModel.addAttribute("medicalRecord", medicalRecord);
		return "/view/add-medical-rec";
	}
	
	@GetMapping("/deleteMedicalRecord")
	public String deleteMedicalRecord(@RequestParam(name = "medicalRecordId") Long medicalRecordId){
		Long patientId = medicalRecordService.getMedicalRecordById(medicalRecordId).getPatient().getPatientId();
		medicalRecordService.deleteMedicalRecord(medicalRecordId);
		
		return "redirect:/showMedicalRec?patientId=" + patientId;
	}

}
