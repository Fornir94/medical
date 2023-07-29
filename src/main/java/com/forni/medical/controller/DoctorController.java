package com.forni.medical.controller;

import com.forni.medical.model.dto.*;
import com.forni.medical.service.DoctorService;
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
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    @Operation(summary = "Get all doctors from database", tags = "Doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DoctorDTO.class))})
    })
    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {

        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @Operation(summary = "Get all doctor's facilities", tags = "Doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = FacilityDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Doctor not found", content = @Content)
    })
    @GetMapping("/{doctorId}/facilities")
    public ResponseEntity<List<FacilityDTO>> getDoctorFacilities(@PathVariable Long doctorId) {
        return ResponseEntity.ok(doctorService.getAllDoctorFacilities(doctorId));
    }

    @Operation(summary = "Get all doctor's visits", tags = "Doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VisitDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Doctor not found", content = @Content)
    })
    @GetMapping("/{doctorId}/visits")
    public ResponseEntity<List<VisitDTO>> getDoctorVisits(@PathVariable Long doctorId) {
        return ResponseEntity.ok(doctorService.getAllDoctorVisits(doctorId));
    }

    @Operation(summary = "Get all doctor's patients", tags = "Doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PatientDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Doctor not found", content = @Content)
    })
    @GetMapping("/{doctorId}/patients")
    public ResponseEntity<List<PatientDTO>> getAllDoctorPatients(@PathVariable Long doctorId) {
        return ResponseEntity.ok(doctorService.getAllDoctorPatients(doctorId));
    }

    @Operation(summary = "Add doctor to database", tags = "Doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DoctorDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Doctor with this email already exists", content = @Content)
    })
    @PostMapping
    public ResponseEntity<DoctorDTO> addDoctor(@RequestBody DoctorCreationDTO doctorCreationDTO) {
        return ResponseEntity.ok(doctorService.addDoctor(doctorCreationDTO));
    }

    @Operation(summary = "Add doctor to facility", tags = "Doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = FacilityDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Doctor with this email already exists", content = @Content),
            @ApiResponse(responseCode = "404", description = "Doctor in this facility already exists", content = @Content)
    })
    @PatchMapping("/{id}/facility/{facilityId}")
    public ResponseEntity<FacilityDTO> addDoctorToFacility(@PathVariable Long id, @PathVariable Long facilityId) {
        return ResponseEntity.ok(doctorService.addDoctorToFacility(facilityId, id));
    }
}
