//$Id$
package com.patientHub.main.exception;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@ControllerAdvice
@SuppressWarnings("unchecked")
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ForbiddenException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<String> handleForbiddenException(ForbiddenException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }
	
	@ExceptionHandler(UnauthorizedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
	
	@ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(AccessDeniedException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error/access-denied";
    }
	
	@ExceptionHandler(PatientNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<JSONObject> patientNotFoundException(Exception ex) {
		JSONObject response = new JSONObject();
		response.put("CODE", HttpStatus.NOT_FOUND);
		response.put("MESSAGE", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	@ExceptionHandler(PatientMandatoryFieldNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<JSONObject> patientMandatoryFieldNotFoundException(Exception ex){
		JSONObject response = new JSONObject();
		response.put("CODE", HttpStatus.NOT_FOUND);
		response.put("MESSAGE", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	@ExceptionHandler(PatientInvalidDataException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public ResponseEntity<JSONObject> patientInvalidDataException(Exception ex){
		JSONObject response = new JSONObject();
		response.put("CODE", HttpStatus.UNPROCESSABLE_ENTITY);
		response.put("MESSAGE", ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
	}
	
	@ExceptionHandler(MedicalRecordNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<JSONObject> medicalRecordNotFoundException(Exception ex) {
		JSONObject response = new JSONObject();
		response.put("CODE", HttpStatus.NOT_FOUND);
		response.put("MESSAGE", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	@ExceptionHandler(MedicalRecMandatoryFieldNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<JSONObject> medicalRecMandatoryFieldNotFoundException(Exception ex){
		JSONObject response = new JSONObject();
		response.put("CODE", HttpStatus.NOT_FOUND);
		response.put("MESSAGE", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	@ExceptionHandler(MedicalRecInvalidDataException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public ResponseEntity<JSONObject> medicalRecInvalidDataException(Exception ex){
		JSONObject response = new JSONObject();
		response.put("CODE", HttpStatus.UNPROCESSABLE_ENTITY);
		response.put("MESSAGE", ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
	}
	
}
