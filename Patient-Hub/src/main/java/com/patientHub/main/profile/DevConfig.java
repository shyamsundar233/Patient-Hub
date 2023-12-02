//$Id$
package com.patientHub.main.profile;


import javax.sql.DataSource;

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

import io.netty.handler.codec.http.HttpMethod;

@Configuration
@EnableWebSecurity
@Profile("dev")
public class DevConfig {
	
	@Value("${role.architect}")
	private String ARCHITECT;
	
	@Value("${role.documentation_specialist}")
	private String DOCUMENTATION_SPECIALIST;
	
	@Value("${role.developer}")
	private String DEVELOPER;
	
	@Value("${role.development_manager}")
	private String DEVELOPMENT_MANAGER;
	
	@Value("${role.quality_engineer}")
	private String QUALITY_ENGINEER;
	
	@Value("${role.security_engineer}")
	private String SECURITY_ENGINEER;
	
	@Value("${role.support_engineer}")
	private String SUPPORT_ENGINEER;
	
	@Value("${role.system_administrator}")
	private String SYSTEM_ADMINISTRATOR;
	
	@Value("${role.team_lead}")
	private String TEAM_LEAD;
	
	@Bean
    DataSource dataSource() { 
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource(); 
          
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/devdb");
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
    				.permitAll()
    				.defaultSuccessUrl("/")
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
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/patient", HttpMethod.GET.toString())).hasAnyRole(SYSTEM_ADMINISTRATOR, ARCHITECT, DEVELOPMENT_MANAGER, QUALITY_ENGINEER, TEAM_LEAD, DEVELOPER, SECURITY_ENGINEER, DOCUMENTATION_SPECIALIST, SUPPORT_ENGINEER)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/patient/**", HttpMethod.GET.toString())).hasAnyRole(SYSTEM_ADMINISTRATOR, ARCHITECT, DEVELOPMENT_MANAGER, QUALITY_ENGINEER, TEAM_LEAD, DEVELOPER, SECURITY_ENGINEER, DOCUMENTATION_SPECIALIST, SUPPORT_ENGINEER)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/patient", HttpMethod.POST.toString())).hasAnyRole(SYSTEM_ADMINISTRATOR, ARCHITECT, DEVELOPMENT_MANAGER, QUALITY_ENGINEER, TEAM_LEAD)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/patient/**", HttpMethod.PUT.toString())).hasAnyRole(SYSTEM_ADMINISTRATOR, ARCHITECT, DEVELOPMENT_MANAGER, QUALITY_ENGINEER, TEAM_LEAD, DEVELOPER, SECURITY_ENGINEER)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/patient/**", HttpMethod.DELETE.toString())).hasAnyRole(SYSTEM_ADMINISTRATOR, TEAM_LEAD)
    				
    				//Medical records API
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/medicalRecord", HttpMethod.GET.toString())).hasAnyRole(SYSTEM_ADMINISTRATOR, ARCHITECT, DEVELOPMENT_MANAGER, QUALITY_ENGINEER, TEAM_LEAD, DEVELOPER, SECURITY_ENGINEER, DOCUMENTATION_SPECIALIST, SUPPORT_ENGINEER)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/medicalRecord/**", HttpMethod.GET.toString())).hasAnyRole(SYSTEM_ADMINISTRATOR, ARCHITECT, DEVELOPMENT_MANAGER, QUALITY_ENGINEER, TEAM_LEAD, DEVELOPER, SECURITY_ENGINEER, DOCUMENTATION_SPECIALIST, SUPPORT_ENGINEER)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/medicalRecord", HttpMethod.POST.toString())).hasAnyRole(SYSTEM_ADMINISTRATOR, ARCHITECT, DEVELOPMENT_MANAGER, QUALITY_ENGINEER, TEAM_LEAD)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/medicalRecord/**", HttpMethod.PUT.toString())).hasAnyRole(SYSTEM_ADMINISTRATOR, ARCHITECT, DEVELOPMENT_MANAGER, QUALITY_ENGINEER, TEAM_LEAD, DEVELOPER, SECURITY_ENGINEER)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/medicalRecord/**", HttpMethod.DELETE.toString())).hasAnyRole(SYSTEM_ADMINISTRATOR, TEAM_LEAD)
    				
    				//URL Restrictions
    				.requestMatchers(new AntPathRequestMatcher("/showAddPatient", HttpMethod.GET.toString())).hasAnyRole(SYSTEM_ADMINISTRATOR, ARCHITECT, DEVELOPMENT_MANAGER, QUALITY_ENGINEER, TEAM_LEAD)
    				.requestMatchers(new AntPathRequestMatcher("/addPatient", HttpMethod.GET.toString())).hasAnyRole(SYSTEM_ADMINISTRATOR, ARCHITECT, DEVELOPMENT_MANAGER, QUALITY_ENGINEER, TEAM_LEAD)
    				.requestMatchers(new AntPathRequestMatcher("/showAddMedicalRec", HttpMethod.GET.toString())).hasAnyRole(SYSTEM_ADMINISTRATOR, ARCHITECT, DEVELOPMENT_MANAGER, QUALITY_ENGINEER, TEAM_LEAD)
    				.requestMatchers(new AntPathRequestMatcher("/addMedicalRecord", HttpMethod.POST.toString())).hasAnyRole(SYSTEM_ADMINISTRATOR, ARCHITECT, DEVELOPMENT_MANAGER, QUALITY_ENGINEER, TEAM_LEAD)
    				.requestMatchers(new AntPathRequestMatcher("/editPatient", HttpMethod.GET.toString())).hasAnyRole(SYSTEM_ADMINISTRATOR, ARCHITECT, DEVELOPMENT_MANAGER, QUALITY_ENGINEER, TEAM_LEAD, DEVELOPER, SECURITY_ENGINEER)
    				.requestMatchers(new AntPathRequestMatcher("/deletePatient", HttpMethod.GET.toString())).hasAnyRole(SYSTEM_ADMINISTRATOR, TEAM_LEAD)
    				.requestMatchers(new AntPathRequestMatcher("/editMedicalRecord", HttpMethod.GET.toString())).hasAnyRole(SYSTEM_ADMINISTRATOR, ARCHITECT, DEVELOPMENT_MANAGER, QUALITY_ENGINEER, TEAM_LEAD, DEVELOPER, SECURITY_ENGINEER)
    				.requestMatchers(new AntPathRequestMatcher("/deleteMedicalRecord", HttpMethod.GET.toString())).hasAnyRole(SYSTEM_ADMINISTRATOR, TEAM_LEAD)
    				.anyRequest().authenticated()
    		);
    	
		return http.build();
	}
}
