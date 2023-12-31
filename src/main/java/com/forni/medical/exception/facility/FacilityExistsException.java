package com.forni.medical.exception.facility;

import com.forni.medical.exception.MedicalException;
import org.springframework.http.HttpStatus;

public class FacilityExistsException extends MedicalException {

    public FacilityExistsException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
