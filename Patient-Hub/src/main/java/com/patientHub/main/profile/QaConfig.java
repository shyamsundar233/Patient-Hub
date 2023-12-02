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
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import io.netty.handler.codec.http.HttpMethod;

@Configuration
@EnableWebSecurity
@Profile("qa")
public class QaConfig {
	
	@Value("${role.qa_analyst}")
    private String QA_ANALYST;

    @Value("${role.automation_engineer}")
    private String AUTOMATION_ENGINEER;

    @Value("${role.documentation_specialist}")
    private String DOCUMENTATION_SPECIALIST;

    @Value("${role.environment_specialist}")
    private String ENVIRONMENT_SPECIALIST;

    @Value("${role.qa_manager}")
    private String QA_MANAGER;

    @Value("${role.performance_engineer}")
    private String PERFORMANCE_ENGINEER;

    @Value("${role.tester}")
    private String TESTER;

    @Value("${role.test_lead}")
    private String TEST_LEAD;
	
	@Bean
    DataSource dataSource() { 
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource(); 
          
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/qadb");
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
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/patient", HttpMethod.GET.toString())).hasAnyRole(QA_ANALYST, AUTOMATION_ENGINEER, DOCUMENTATION_SPECIALIST, ENVIRONMENT_SPECIALIST, QA_MANAGER, PERFORMANCE_ENGINEER, TESTER, TEST_LEAD)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/patient/**", HttpMethod.GET.toString())).hasAnyRole(QA_ANALYST, AUTOMATION_ENGINEER, DOCUMENTATION_SPECIALIST, ENVIRONMENT_SPECIALIST, QA_MANAGER, PERFORMANCE_ENGINEER, TESTER, TEST_LEAD)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/patient", HttpMethod.POST.toString())).hasAnyRole(AUTOMATION_ENGINEER, QA_MANAGER, PERFORMANCE_ENGINEER, TEST_LEAD)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/patient/**", HttpMethod.PUT.toString())).hasAnyRole(QA_ANALYST, AUTOMATION_ENGINEER, DOCUMENTATION_SPECIALIST, QA_MANAGER, PERFORMANCE_ENGINEER, TEST_LEAD)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/patient/**", HttpMethod.DELETE.toString())).hasAnyRole(QA_MANAGER, PERFORMANCE_ENGINEER, TEST_LEAD)
    				
    				//Medical records API
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/medicalRecord", HttpMethod.GET.toString())).hasAnyRole(QA_ANALYST, AUTOMATION_ENGINEER, DOCUMENTATION_SPECIALIST, ENVIRONMENT_SPECIALIST, QA_MANAGER, PERFORMANCE_ENGINEER, TESTER, TEST_LEAD)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/medicalRecord/**", HttpMethod.GET.toString())).hasAnyRole(QA_ANALYST, AUTOMATION_ENGINEER, DOCUMENTATION_SPECIALIST, ENVIRONMENT_SPECIALIST, QA_MANAGER, PERFORMANCE_ENGINEER, TESTER, TEST_LEAD)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/medicalRecord", HttpMethod.POST.toString())).hasAnyRole(AUTOMATION_ENGINEER, QA_MANAGER, PERFORMANCE_ENGINEER, TEST_LEAD)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/medicalRecord/**", HttpMethod.PUT.toString())).hasAnyRole(QA_ANALYST, AUTOMATION_ENGINEER, DOCUMENTATION_SPECIALIST, QA_MANAGER, PERFORMANCE_ENGINEER, TEST_LEAD)
    				.requestMatchers(new AntPathRequestMatcher("/api/v1/medicalRecord/**", HttpMethod.DELETE.toString())).hasAnyRole(QA_MANAGER, PERFORMANCE_ENGINEER, TEST_LEAD)
    				.anyRequest().authenticated()
    		);
    	
		return http.build();
	}
}