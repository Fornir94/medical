package com.forni.medical.exception.visitexception;

import com.forni.medical.exception.MedicalException;
import org.springframework.http.HttpStatus;

public class VisitNotFoundException extends MedicalException {

    public VisitNotFoundException(String message){
        super(message, HttpStatus.NOT_FOUND);
    }

}
