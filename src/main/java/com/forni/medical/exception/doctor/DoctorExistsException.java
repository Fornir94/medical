package com.forni.medical.exception.doctor;

import com.forni.medical.exception.MedicalException;
import org.springframework.http.HttpStatus;

public class DoctorExistsException extends MedicalException {

    public DoctorExistsException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
