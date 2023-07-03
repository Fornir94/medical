package com.forni.medical.handler;

import com.forni.medical.exception.IllegalPatientDataException;
import com.forni.medical.exception.MedicalException;
import com.forni.medical.exception.PatientExistsException;
import com.forni.medical.exception.PatientNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler{

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<String> patientExceptionErrorResponse(PatientNotFoundException e){
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(PatientExistsException.class)
    public ResponseEntity<String> patientExceptionErrorResponse(PatientExistsException e){
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(IllegalPatientDataException.class)
    public ResponseEntity<String> patientExceptionErrorResponse(IllegalPatientDataException e){
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(MedicalException.class)
    public ResponseEntity<String> patientExceptionErrorResponse(MedicalException e){
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> patientExceptionErrorResponse(RuntimeException e){
        return ResponseEntity.status(500).body("Unknown error exception");
    }
}
