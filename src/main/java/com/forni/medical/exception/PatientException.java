package com.forni.medical.exception;

import org.springframework.http.HttpStatus;

public class PatientException extends MedicalException{
    public PatientException (String message, HttpStatus httpStatus){
        super(message, httpStatus);
    }
}
