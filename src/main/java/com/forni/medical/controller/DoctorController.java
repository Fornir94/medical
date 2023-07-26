package com.forni.medical.controller;

import com.forni.medical.model.dto.*;
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

    @GetMapping("/{doctorId}/facilities")
    public List<FacilityDTO> getDoctorFacilities(@PathVariable Long doctorId) {
        return doctorService.getAllDoctorFacilities(doctorId);
    }

    @GetMapping("/{doctorId}/visits")
    public List<VisitDTO> getDoctorVisits(@PathVariable Long doctorId) {
        return doctorService.getAllDoctorVisits(doctorId);
    }

    @GetMapping("/{doctorId}/patients")
    public List<PatientDTO> getAllDoctorPatients(@PathVariable Long doctorId) {
        return doctorService.getAllDoctorPatients(doctorId);
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
