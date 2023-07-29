package com.forni.medical.exception.patient;

import com.forni.medical.exception.MedicalException;
import org.springframework.http.HttpStatus;

public class PatientNotFoundException extends MedicalException {
    public PatientNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
