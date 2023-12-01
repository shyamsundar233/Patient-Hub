//$Id$
package com.patientHub.main.exception;

@SuppressWarnings("serial")
public class ForbiddenException extends RuntimeException {
    
	public ForbiddenException(String message) {
        super(message);
    }
	
}

