package com.forni.medical.exception.visit;

import com.forni.medical.exception.MedicalException;
import org.springframework.http.HttpStatus;

public class VisitDateException extends MedicalException {
    public VisitDateException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
