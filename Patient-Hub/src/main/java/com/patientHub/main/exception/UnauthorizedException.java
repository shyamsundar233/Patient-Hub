//$Id$
package com.patientHub.main.exception;

@SuppressWarnings("serial")
public class UnauthorizedException extends RuntimeException{
	
	public UnauthorizedException(String message) {
        super(message);
    }

}
