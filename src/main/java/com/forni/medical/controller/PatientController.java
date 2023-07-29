package com.forni.medical.controller;

import com.forni.medical.model.dto.PatientCreationDTO;
import com.forni.medical.model.dto.PatientEditDTO;
import com.forni.medical.model.dto.PatientDTO;
import com.forni.medical.model.dto.VisitDTO;
import com.forni.medical.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    @Operation(summary = "Get all patients added to database", tags = "Patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PatientDTO.class))})})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        return ResponseEntity.ok(patientService.allPatients());
    }

    @Operation(summary = "Get patient by email added to database", tags = "Patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PatientDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Patient not found", content = @Content)
    })
    @GetMapping("/{email}")
    public ResponseEntity<PatientDTO> getPatientByEmail(@PathVariable String email) {
        return ResponseEntity.ok(patientService.patientByEmail(email));
    }

    @Operation(summary = "Get patient visits by an email", tags = "Patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VisitDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Patient not found", content = @Content)
    })
    @GetMapping("/{email}/visits")
    public ResponseEntity<List<VisitDTO>> getPatientVisits(@PathVariable String email) {
        return ResponseEntity.ok(patientService.allPatientVisits(email));
    }

    @Operation(summary = "Add patient do database", tags = "Patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PatientDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content)
    })
    @PostMapping
    public ResponseEntity<PatientDTO> addPatient(@RequestBody PatientCreationDTO patientCreationDTO) {
        return ResponseEntity.ok(patientService.addNewPatient(patientCreationDTO));
    }

    @Operation(summary = "Delete patient form database", tags = "Patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Patient not found", content = @Content)
    })
    @DeleteMapping("/{email}")
    public void deletePatient(@PathVariable String email) {
        patientService.deletePatient(email);
    }

    @Operation(summary = "Update patient data from database", tags = "Patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PatientDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Patient not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content)
    })
    @PutMapping("/{email}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable String email, @RequestBody PatientEditDTO updatePatient) {
        return ResponseEntity.ok(patientService.update(email, updatePatient));
    }

    @Operation(summary = "Update patient's password", tags = "Patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password updated",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Patient not found", content = @Content)
    })
    @PatchMapping("/{email}")
    public void updatePassword(@PathVariable String email, @RequestBody String password) {
        patientService.updatePassword(email, password);
    }
}
