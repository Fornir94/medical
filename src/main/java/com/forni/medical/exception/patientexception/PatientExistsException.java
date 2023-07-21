package com.forni.medical.exception.patientexception;

import com.forni.medical.exception.MedicalException;
import org.springframework.http.HttpStatus;

public class PatientExistsException extends MedicalException {
    public PatientExistsException(String message){
        super(message, HttpStatus.CONFLICT);
    }
}
