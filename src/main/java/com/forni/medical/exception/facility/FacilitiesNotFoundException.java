package com.forni.medical.exception.facility;

import com.forni.medical.exception.MedicalException;
import org.springframework.http.HttpStatus;

public class FacilitiesNotFoundException extends MedicalException {
    public FacilitiesNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
