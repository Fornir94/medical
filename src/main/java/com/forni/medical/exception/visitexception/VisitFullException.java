package com.forni.medical.exception.visitexception;

import com.forni.medical.exception.MedicalException;
import org.springframework.http.HttpStatus;

public class VisitFullException extends MedicalException {
    public VisitFullException(String message){
        super(message, HttpStatus.FORBIDDEN);
    }
}
