package com.patientHub.main.controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.patientHub.main.model.Iamuser;
import com.patientHub.main.model.MedicalRecord;
import com.patientHub.main.model.NewUser;
import com.patientHub.main.model.Patient;
import com.patientHub.main.model.Userrole;
import com.patientHub.main.service.IamuserService;
import com.patientHub.main.service.MedicalRecordService;
import com.patientHub.main.service.PatientService;
import com.patientHub.main.service.UserroleService;

@Controller
public class MainController {
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private MedicalRecordService medicalRecordService;
	
	@Autowired
	private IamuserService iamuserService;
	
	@Autowired
	private UserroleService userroleService;
	
	@Value("${patient.gender}")
	private List<String> genderList;
	
	@Value("${patient.type}")
	private List<String> typesList;
	
	@Value("${password.regex}")
	private String passwordRegex; 
	
	@Value("${roles.list}")
	private List<String> rolesList;
	
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
		return "view/add-medical-rec";
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
		return "view/add-medical-rec";
	}
	
	@GetMapping("/deleteMedicalRecord")
	public String deleteMedicalRecord(@RequestParam(name = "medicalRecordId") Long medicalRecordId){
		Long patientId = medicalRecordService.getMedicalRecordById(medicalRecordId).getPatient().getPatientId();
		medicalRecordService.deleteMedicalRecord(medicalRecordId);
		
		return "redirect:/showMedicalRec?patientId=" + patientId;
	}
	
	@GetMapping("/showAddUser")
	public String showAddUser(Model theModel) {
		theModel.addAttribute("newUser", new NewUser());
		theModel.addAttribute("userRoles", rolesList);
		return "view/add-user";
	}
	
	@PostMapping("/createUser")
	public String createUser(@ModelAttribute NewUser newUser) throws Exception {
		validateNewUser(newUser);
		
		Iamuser iamuser = new Iamuser();		
		iamuser.setUserName(newUser.getUserName());
		iamuser.setUserPassword(newUser.getUserPassword());
		iamuser.setUserStatus(1);
		
		iamuserService.saveIamUser(iamuser);
		
		Userrole userrole = new Userrole();
		userrole.setUserName(newUser.getUserName());
		userrole.setUserRole(newUser.getUserRole());
		
		userroleService.saveUserRole(userrole);
		
		return "redirect:/logout";
	}

	private void validateNewUser(NewUser newUser) throws Exception {
		
		String userName = newUser.getUserName();
		if(userName.isEmpty() || userName.length() > 50 || userName.chars().anyMatch(Character::isDigit)) {
			throw new Exception("Invalid user name");
		}
		
		String userPassword = newUser.getUserPassword();
		if(userPassword.isEmpty() || userPassword.length() < 8 || userPassword.length() > 50 || !validatePassword(userPassword)) {
			throw new Exception("Invalid user password");
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		userPassword = passwordEncoder.encode(userPassword);
		newUser.setUserPassword(userPassword);
		
		String userRole = newUser.getUserRole();
		userRole = "ROLE_" + userRole.toUpperCase();
		newUser.setUserRole(userRole);
		
	}
	
	/*
	 * At least 8 characters long
	 * Contains at least one digit (0-9)
	 * Contains at least one lowercase letter (a-z)
	 * Contains at least one uppercase letter (A-Z)
	 * Contains at least one special character from the set [@#$%^&+=!]
	 * Does not contain whitespaces
	 * */
	
	private boolean validatePassword(String userPassword) {
		Pattern passwordPattern = Pattern.compile(passwordRegex);
		Matcher matcher = passwordPattern.matcher(userPassword);
		return matcher.matches();
	}

}
