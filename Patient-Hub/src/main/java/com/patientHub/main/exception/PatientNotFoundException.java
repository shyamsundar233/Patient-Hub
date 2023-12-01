//$Id$
package com.patientHub.main.exception;


@SuppressWarnings("serial")
public class PatientNotFoundException extends RuntimeException{
	
	public PatientNotFoundException(String message) {
		super(message);
	}

}

