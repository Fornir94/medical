package com.forni.medical.exception.visitexception;

import com.forni.medical.exception.MedicalException;
import org.springframework.http.HttpStatus;

public class VisitExistsException extends MedicalException {

    public VisitExistsException(String message){
        super(message, HttpStatus.BAD_REQUEST);
    }
}
