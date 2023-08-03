package com.forni.medical.controller;

import com.forni.medical.model.dto.DoctorDTO;
import com.forni.medical.model.dto.FacilityCreationDTO;
import com.forni.medical.model.dto.FacilityDTO;
import com.forni.medical.model.dto.VisitDTO;
import com.forni.medical.service.FacilityService;
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
@RequestMapping("/facilities")
public class FacilityController {

    private final FacilityService facilityService;

    @Operation(summary = "Get all facilities from database", tags = "Facility")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = FacilityDTO.class))})
    })
    @GetMapping
    public ResponseEntity<List<FacilityDTO>> getAllFacilities() {
        log.info("List od facilities have been returned");
        return ResponseEntity.ok(facilityService.getAllFacility());
    }

    @Operation(summary = "Get all doctors add to facility", tags = "Facility")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DoctorDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Facility not found", content = @Content)
    })
    @GetMapping("/{id}/doctors")
    public ResponseEntity<List<DoctorDTO>> getAllFacilityDoctor(@PathVariable Long id) {
        log.info("All doctors from facility with id: {} , have been returned", id);
        return ResponseEntity.ok(facilityService.getAllFacilityDoctors(id));
    }

    @Operation(summary = "Add facility to database", tags = "Facility")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Facility add",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = FacilityDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Facility with this name already exists", content = @Content)
    })
    @PostMapping
    public ResponseEntity<FacilityDTO> addFacility(@RequestBody FacilityCreationDTO facilityCreationDTO) {
        log.info("Facility with: name: {}, city: {}, postcode: {}, street: {}, street number: {}, has been add to database", facilityCreationDTO.getName(), facilityCreationDTO.getCity(), facilityCreationDTO.getPostCode(), facilityCreationDTO.getStreet(), facilityCreationDTO.getStreetNumber());
        return ResponseEntity.ok(facilityService.addFacility(facilityCreationDTO));
    }
}
