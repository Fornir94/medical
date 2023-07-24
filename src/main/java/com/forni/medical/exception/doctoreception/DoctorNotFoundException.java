package com.forni.medical.exception.doctoreception;

import com.forni.medical.exception.MedicalException;
import org.springframework.http.HttpStatus;

public class DoctorNotFoundException extends MedicalException {

    public DoctorNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
