package com.forni.medical.handler;

import com.forni.medical.exception.doctor.DoctorExistsException;
import com.forni.medical.exception.doctor.DoctorNotFoundException;
import com.forni.medical.exception.facility.FacilitiesNotFoundException;
import com.forni.medical.exception.facility.FacilityExistsException;
import com.forni.medical.exception.patient.IllegalPatientDataException;
import com.forni.medical.exception.MedicalException;
import com.forni.medical.exception.patient.PatientExistsException;
import com.forni.medical.exception.patient.PatientNotFoundException;
import com.forni.medical.exception.visit.VisitDateException;
import com.forni.medical.exception.visit.VisitBookedException;
import com.forni.medical.exception.visit.VisitNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<String> patientExceptionErrorResponse(PatientNotFoundException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(PatientExistsException.class)
    public ResponseEntity<String> patientExceptionErrorResponse(PatientExistsException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(IllegalPatientDataException.class)
    public ResponseEntity<String> patientExceptionErrorResponse(IllegalPatientDataException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(MedicalException.class)
    public ResponseEntity<String> patientExceptionErrorResponse(MedicalException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> patientExceptionErrorResponse(RuntimeException e) {
        return ResponseEntity.status(500).body("Unknown error exception");
    }

    @ExceptionHandler(VisitNotFoundException.class)
    public ResponseEntity<String> visitExceptionErrorResponse(VisitNotFoundException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(VisitDateException.class)
    public ResponseEntity<String> visitExceptionErrorResponse(VisitDateException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(VisitBookedException.class)
    public ResponseEntity<String> visitExceptionErrorResponse(VisitBookedException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(FacilityExistsException.class)
    public ResponseEntity<String> facilityExceptionResponse(FacilityExistsException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(DoctorExistsException.class)
    public ResponseEntity<String> doctorExceptionResponse(DoctorExistsException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(FacilitiesNotFoundException.class)
    public ResponseEntity<String> facilityExceptionResponse(FacilitiesNotFoundException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<String> doctorExceptionResponse(DoctorNotFoundException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
}
