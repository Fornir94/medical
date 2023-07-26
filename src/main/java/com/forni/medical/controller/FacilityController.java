package com.forni.medical.controller;

import com.forni.medical.model.dto.DoctorDTO;
import com.forni.medical.model.dto.FacilityCreationDTO;
import com.forni.medical.model.dto.FacilityDTO;
import com.forni.medical.service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/facilities")
public class FacilityController {

    private final FacilityService facilityService;

    @GetMapping
    public List<FacilityDTO> getAllFacilities() {
        return facilityService.getAllFacility();
    }

    @GetMapping("/{id}/doctors")
    public List<DoctorDTO> getAllFacilityDoctor(@PathVariable Long id) {
        return facilityService.getAllFacilityDoctors(id);
    }

    @PostMapping
    public FacilityDTO addFacility(@RequestBody FacilityCreationDTO facilityCreationDTO) {
        return facilityService.addFacility(facilityCreationDTO);
    }
}
