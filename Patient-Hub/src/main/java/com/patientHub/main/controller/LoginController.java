//$Id$
package com.patientHub.main.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.patientHub.main.model.Login;

@Controller
public class LoginController {
	
//	@GetMapping("/logoutUser")
//    public String logout(HttpServletRequest request, HttpServletResponse response) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication != null) {
//        	SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
//        	securityContextLogoutHandler.setClearAuthentication(true);
//        	securityContextLogoutHandler.logout(request, response, authentication);
//        	
//        	SecurityContextHolder.clearContext();
//        }
//
//        return "redirect:/login?logout";
//    }

    @GetMapping("/login")
    public String showLoginPage(Model theModel) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        } else {
            theModel.addAttribute("login", new Login());
            return "login-page";
        }
    }

    @GetMapping("/access-denied")
    public String showAccessDeniedPage() {
        return "access-denied";
    }

}
