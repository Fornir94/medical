package com.forni.medical.controller;

import com.forni.medical.model.dto.PatientDTO;
import com.forni.medical.model.dto.VisitCreationDTO;
import com.forni.medical.model.dto.VisitDTO;
import com.forni.medical.service.VisitService;
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
@RequestMapping("/visits")
public class VisitController {

    private final VisitService visitService;

    @Operation(summary = "Get all visits added to database", tags = "Visit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VisitDTO.class))})})
    @GetMapping
    public ResponseEntity<List<VisitDTO>> getAllVisits() {

        return ResponseEntity.ok(visitService.getAllVisits());
    }

    @Operation(summary = "Get visit by id added to database", tags = "Visit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VisitDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Visit not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<VisitDTO> getVisit(@PathVariable Long id) {
        return ResponseEntity.ok(visitService.findVisit(id));
    }

    @Operation(summary = "Add visit to database", tags = "Visit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VisitDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Visit with this date is before then actual, or time is different than a full quarter of an hour, or The time of the visit coincides with another visit", content = @Content)
    })
    @PostMapping
    public ResponseEntity<VisitDTO> addVisit(@RequestBody VisitCreationDTO visitCreationDTO) {
        return ResponseEntity.ok(visitService.addVisit(visitCreationDTO));
    }

    @Operation(summary = "Add patient to visit", tags = "Visit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VisitDTO.class))}),
            @ApiResponse(responseCode = "400", description = "This Visit is booked", content = @Content),
            @ApiResponse(responseCode = "404", description = "Visit not found, or patient not found", content = @Content)
    })
    @PatchMapping("/{id}/patient/{patientId}")
    public ResponseEntity<VisitDTO> addPatientToVisit(@PathVariable Long id, @PathVariable Long patientId) {
        return ResponseEntity.ok(visitService.addPatientToVisit(id, patientId));
    }

    @Operation(summary = "Add doctor to visit", tags = "Visit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VisitDTO.class))}),
            @ApiResponse(responseCode = "400", description = "The docotor has been already added", content = @Content),
            @ApiResponse(responseCode = "404", description = "Visit not found, or doctor not found", content = @Content)
    })
    @PatchMapping("/{visitId}/doctor/{doctorId}")
    public ResponseEntity<VisitDTO> addDoctorToVisit(@PathVariable Long visitId, @PathVariable Long doctorId) {
        return ResponseEntity.ok(visitService.addDoctorToVisit(visitId, doctorId));
    }

    @Operation(summary = "Delete visit form database", tags = "Visit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visit deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Visit not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public void deleteVisit(@PathVariable Long id) {
        visitService.deleteVisit(id);
    }
}
