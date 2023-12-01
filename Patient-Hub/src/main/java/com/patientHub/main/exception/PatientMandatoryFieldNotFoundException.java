//$Id$
package com.patientHub.main.exception;

@SuppressWarnings("serial")
public class PatientMandatoryFieldNotFoundException extends Exception{
	
	public PatientMandatoryFieldNotFoundException(String message) {
		super(message);
	}

}
