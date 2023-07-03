package com.forni.medical.exception;

import org.springframework.http.HttpStatus;

public class IllegalPatientDataException extends MedicalException{
    public IllegalPatientDataException(String message){
        super(message, HttpStatus.FORBIDDEN);
    }
}
