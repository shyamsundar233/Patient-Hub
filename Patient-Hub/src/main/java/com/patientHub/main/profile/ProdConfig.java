//$Id$
package com.patientHub.main.profile;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.patientHub.main.exception.CustomAccessDeniedHandler;

import io.netty.handler.codec.http.HttpMethod;

@Configuration
@EnableWebSecurity
@Profile("prod")
public class ProdConfig {
	
	@Value("${role.admin}")
    private String ADMIN;

    @Value("${role.caregiver}")
    private String CAREGIVER;

    @Value("${role.doctor}")
    private String DOCTOR;

    @Value("${role.insurance_agent}")
    private String INSURANCE_AGENT;

    @Value("${role.lab_technician}")
    private String LAB_TECHNICIAN;

    @Value("${role.nurse}")
    private String NURSE;

    @Value("${role.patient}")
    private String PATIENT;

    @Value("${role.pharmacist}")
    private String PHARMACIST;

    @Value("${role.receptionist}")
    private String RECEPTIONIST;
    
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;
	
	@Bean
    DataSource dataSource() { 
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource(); 
          
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/proddb?createDatabaseIfNotExist=true");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("Bornjuly@2001");
        driverManagerDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
          
        return driverManagerDataSource; 
    }
	
	@Bean
	PasswordEncoder passwordEncoder(DataSource dataSource) {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	UserDetailsManager userDetailsManager(DataSource dataSource) {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		
		jdbcUserDetailsManager.setUsersByUsernameQuery(
				"select user_name, user_password, user_status from iamuser where user_name=?"
		);
		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
				"select user_name, user_role from userrole where user_name=?"
		);
		
		return jdbcUserDetailsManager;
	}
	
	@Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception{
    	
    	http
    		.csrf(csrf -> csrf.disable())
    		.httpBasic(Customizer.withDefaults())
    		.formLogin(form -> form
    				.loginPage("/login")
    				.loginProcessingUrl("/processLoginForm")    				
    				.defaultSuccessUrl("/") 
    				.permitAll()
    		)
    		.logout(logout -> logout
    				.logoutUrl("/logout")
    				.logoutSuccessUrl("/login?logout")
    				.invalidateHttpSession(true)
    				.deleteCookies("JSESSIONID")
    				.permitAll()
    		)
    		.authorizeHttpRequests((authorize) -> authorize
    				
    				//Patient API
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/patient", HttpMethod.GET.toString())).hasAnyRole(ADMIN, CAREGIVER, DOCTOR, INSURANCE_AGENT, LAB_TECHNICIAN, NURSE, PATIENT, PHARMACIST, RECEPTIONIST)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/patient/**", HttpMethod.GET.toString())).hasAnyRole(ADMIN, CAREGIVER, DOCTOR, INSURANCE_AGENT, LAB_TECHNICIAN, NURSE, PATIENT, PHARMACIST, RECEPTIONIST)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/patient", HttpMethod.POST.toString())).hasAnyRole(ADMIN, DOCTOR, INSURANCE_AGENT, LAB_TECHNICIAN, PHARMACIST)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/patient/**", HttpMethod.PUT.toString())).hasAnyRole(ADMIN, DOCTOR, INSURANCE_AGENT, LAB_TECHNICIAN, NURSE, PHARMACIST)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/patient/**", HttpMethod.DELETE.toString())).hasAnyRole(ADMIN, DOCTOR, LAB_TECHNICIAN)
    				
    				//Medical records API
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/medicalRecord", HttpMethod.GET.toString())).hasAnyRole(ADMIN, CAREGIVER, DOCTOR, INSURANCE_AGENT, LAB_TECHNICIAN, NURSE, PATIENT, PHARMACIST, RECEPTIONIST)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/medicalRecord/**", HttpMethod.GET.toString())).hasAnyRole(ADMIN, CAREGIVER, DOCTOR, INSURANCE_AGENT, LAB_TECHNICIAN, NURSE, PATIENT, PHARMACIST, RECEPTIONIST)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/medicalRecord", HttpMethod.POST.toString())).hasAnyRole(ADMIN, DOCTOR, INSURANCE_AGENT, LAB_TECHNICIAN, PHARMACIST)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/medicalRecord/**", HttpMethod.PUT.toString())).hasAnyRole(ADMIN, DOCTOR, INSURANCE_AGENT, LAB_TECHNICIAN, NURSE, PHARMACIST)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/medicalRecord/**", HttpMethod.DELETE.toString())).hasAnyRole(ADMIN, DOCTOR, LAB_TECHNICIAN)
    				
    				//URL Restrictions
    				.requestMatchers(new AntPathRequestMatcher("/showAddPatient", HttpMethod.GET.toString())).hasAnyRole(ADMIN, DOCTOR, INSURANCE_AGENT, LAB_TECHNICIAN, PHARMACIST)
    				.requestMatchers(new AntPathRequestMatcher("/addPatient", HttpMethod.POST.toString())).hasAnyRole(ADMIN, DOCTOR, INSURANCE_AGENT, LAB_TECHNICIAN, PHARMACIST)
    				.requestMatchers(new AntPathRequestMatcher("/showAddMedicalRec", HttpMethod.GET.toString())).hasAnyRole(ADMIN, DOCTOR, INSURANCE_AGENT, LAB_TECHNICIAN, PHARMACIST)
    				.requestMatchers(new AntPathRequestMatcher("/addMedicalRecord", HttpMethod.POST.toString())).hasAnyRole(ADMIN, DOCTOR, INSURANCE_AGENT, LAB_TECHNICIAN, PHARMACIST)
    				.requestMatchers(new AntPathRequestMatcher("/editPatient", HttpMethod.GET.toString())).hasAnyRole(ADMIN, DOCTOR, INSURANCE_AGENT, LAB_TECHNICIAN, PHARMACIST)
    				.requestMatchers(new AntPathRequestMatcher("/deletePatient", HttpMethod.GET.toString())).hasAnyRole(ADMIN, DOCTOR, LAB_TECHNICIAN)
    				.requestMatchers(new AntPathRequestMatcher("/editMedicalRecord", HttpMethod.GET.toString())).hasAnyRole(ADMIN, DOCTOR, INSURANCE_AGENT, LAB_TECHNICIAN, NURSE, PHARMACIST)
    				.requestMatchers(new AntPathRequestMatcher("/deleteMedicalRecord", HttpMethod.GET.toString())).hasAnyRole(ADMIN, DOCTOR, LAB_TECHNICIAN)
    				.requestMatchers(new AntPathRequestMatcher("/showAddUser")).permitAll()
    				.requestMatchers(new AntPathRequestMatcher("/createUser")).permitAll()
    				.anyRequest().authenticated()
    		)
    		.exceptionHandling(exception -> exception
    				.accessDeniedHandler(customAccessDeniedHandler)
    		);
    	
		return http.build();
	}
}
