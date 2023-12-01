//$Id$
package com.patientHub.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.patientHub.main.model.Login;

@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String showLoginPage(Model theModel) {
		theModel.addAttribute("login", new Login());
		return "login-page";
	}
	
	@GetMapping("/access-denied")
	public String showAccessDeniedPage() {
		return "access-denied";
	}
}
