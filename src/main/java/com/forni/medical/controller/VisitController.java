package com.forni.medical.controller;

import com.forni.medical.model.dto.VisitCreationDTO;
import com.forni.medical.model.dto.VisitDTO;
import com.forni.medical.service.VisitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/visits")
public class VisitController {

    private final VisitService visitService;

    @Operation(summary = "Get all visits added to database", tags = "Visit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VisitDTO.class))})})
    @GetMapping
    public ResponseEntity<List<VisitDTO>> getAllVisits() {
        log.info("Trying to fetch all visits");
        var visits=visitService.getAllVisits();
        log.info("List of all visits have been returned");
        return ResponseEntity.ok(visits);
    }

    @Operation(summary = "Get visit by id added to database", tags = "Visit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VisitDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Visit not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<VisitDTO> getVisit(@PathVariable Long id) {
        log.info("Trying to get visit by id: {}", id);
        var visit=visitService.findVisit(id);
        log.info("Visit with id: {}, has been returned", id);
        return ResponseEntity.ok(visit);
    }

    @Operation(summary = "Add visit to database", tags = "Visit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VisitDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Visit with this date is before then actual, or time is different than a full quarter of an hour, or The time of the visit coincides with another visit", content = @Content)
    })
    @PostMapping
    public ResponseEntity<VisitDTO> addVisit(@RequestBody VisitCreationDTO visitCreationDTO) {
        log.info("Trying to add visit to database");
        var visit = visitService.addVisit(visitCreationDTO);
        log.info("Visit with body: {}, has been add to database", visitCreationDTO);
        return ResponseEntity.ok(visit);
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
        log.info("Trying to add patient with id: {}, to visit with id: {}", patientId, id);
        var visit = visitService.addPatientToVisit(id, patientId);
        log.info("Patient with id: {}, has been add to visit with id: {}", patientId, id);
        return ResponseEntity.ok(visit);
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
        log.info("Trying to add doctor with id: {}, to visit with id: {}", doctorId, visitId);
        var visit = visitService.addDoctorToVisit(visitId, doctorId);
        log.info("Doctor with id: {}, has been add to visit with id: {}",doctorId, visitId);
        return ResponseEntity.ok(visit);
    }

    @Operation(summary = "Delete visit form database", tags = "Visit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visit deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Visit not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public void deleteVisit(@PathVariable Long id) {
        log.info("Trying to delete visit with id: {}, form database", id);
        visitService.deleteVisit(id);
        log.info("Visit with id: {}, has been deleted", id);
    }
}
