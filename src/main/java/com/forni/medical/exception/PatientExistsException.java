package com.forni.medical.exception;

import org.springframework.http.HttpStatus;

public class PatientExistsException extends MedicalException{
    public PatientExistsException(String message){
        super(message, HttpStatus.IM_USED);
    }
}
