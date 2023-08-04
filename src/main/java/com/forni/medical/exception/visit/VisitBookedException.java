package com.forni.medical.exception.visit;

import com.forni.medical.exception.MedicalException;
import org.springframework.http.HttpStatus;

public class VisitBookedException extends MedicalException {
    public VisitBookedException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
