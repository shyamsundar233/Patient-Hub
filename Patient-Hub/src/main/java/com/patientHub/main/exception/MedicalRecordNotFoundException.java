//$Id$
package com.patientHub.main.exception;


@SuppressWarnings("serial")
public class MedicalRecordNotFoundException extends RuntimeException{
	
	public MedicalRecordNotFoundException(String message) {
		super(message);
	}

}

