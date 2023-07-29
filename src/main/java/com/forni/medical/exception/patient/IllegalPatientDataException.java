package com.forni.medical.exception.patient;

import com.forni.medical.exception.MedicalException;
import org.springframework.http.HttpStatus;

public class IllegalPatientDataException extends MedicalException {
    public IllegalPatientDataException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
