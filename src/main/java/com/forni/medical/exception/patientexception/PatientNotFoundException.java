package com.forni.medical.exception.patientexception;

import com.forni.medical.exception.MedicalException;
import org.springframework.http.HttpStatus;

public class PatientNotFoundException extends MedicalException {
    public PatientNotFoundException(String message){
        super(message, HttpStatus.NOT_FOUND);
    }
}
