package com.forni.medical.exception.patient;

import com.forni.medical.exception.MedicalException;
import org.springframework.http.HttpStatus;

public class PatientException extends MedicalException {
    public PatientException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
