//$Id$
package com.patientHub.main.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import io.netty.handler.codec.http.HttpMethod;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Value("${role.admin}")
	private String ADMIN;
	
	@Value("${role.srdoctor}")
	private String SRDOCTOR;
	
	@Value("${role.doctor}")
	private String DOCTOR;
	
	@Value("${role.trainee}")
	private String TRAINEE;
	
	@Bean
    DataSource dataSource() { 
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource(); 
          
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/springdb"); 
        driverManagerDataSource.setUsername("root"); 
        driverManagerDataSource.setPassword("Bornjuly@2001"); 
        driverManagerDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver"); 
          
        return driverManagerDataSource; 
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
    		)
    		.logout(logout -> logout
    				.logoutUrl("/logout")
    				.invalidateHttpSession(true)
    				.permitAll()
    		)
    		.authorizeHttpRequests((authorize) -> authorize
    				
    				//Patient API
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/patient", HttpMethod.GET.toString())).hasAnyRole(ADMIN, SRDOCTOR, DOCTOR)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/patient/**", HttpMethod.GET.toString())).hasAnyRole(ADMIN, SRDOCTOR, DOCTOR, TRAINEE)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/patient", HttpMethod.POST.toString())).hasAnyRole(ADMIN, SRDOCTOR, DOCTOR)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/patient/**", HttpMethod.PUT.toString())).hasAnyRole(ADMIN, SRDOCTOR, DOCTOR)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/patient/**", HttpMethod.DELETE.toString())).hasAnyRole(ADMIN, SRDOCTOR)
    				
    				//Medical records API
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/medicalRecord", HttpMethod.GET.toString())).hasAnyRole(ADMIN, SRDOCTOR, DOCTOR, TRAINEE)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/medicalRecord/**", HttpMethod.GET.toString())).hasAnyRole(ADMIN, SRDOCTOR, DOCTOR, TRAINEE)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/medicalRecord", HttpMethod.POST.toString())).hasAnyRole(ADMIN, SRDOCTOR, DOCTOR)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/medicalRecord/**", HttpMethod.PUT.toString())).hasAnyRole(ADMIN, SRDOCTOR, DOCTOR)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/medicalRecord/**", HttpMethod.DELETE.toString())).hasAnyRole(ADMIN, SRDOCTOR)
    				.anyRequest().authenticated()
    		);
    	
		return http.build();
	}

}
