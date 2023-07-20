package com.forni.medical.exception.visitexception;

import com.forni.medical.exception.MedicalException;
import org.springframework.http.HttpStatus;

public class VisitDateException extends MedicalException {
    public VisitDateException(String message){
        super(message, HttpStatus.FORBIDDEN);
    }
}
