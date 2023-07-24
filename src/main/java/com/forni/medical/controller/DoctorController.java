package com.forni.medical.controller;

import com.forni.medical.model.dto.DoctorCreationDTO;
import com.forni.medical.model.dto.DoctorDTO;
import com.forni.medical.model.dto.FacilityDTO;
import com.forni.medical.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    @GetMapping
    public List<DoctorDTO> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @PostMapping
    public DoctorDTO addDoctor(@RequestBody DoctorCreationDTO doctorCreationDTO) {
        return doctorService.addDoctor(doctorCreationDTO);
    }

    @PatchMapping("/{id}/facility/{facilityId}")
    public FacilityDTO addDoctorToFacility(@PathVariable Long id, @PathVariable Long facilityId) {
        return doctorService.addDoctorToFacility(facilityId, id);
    }
}
