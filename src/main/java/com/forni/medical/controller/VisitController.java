package com.forni.medical.controller;

import com.forni.medical.model.dto.PatientDTO;
import com.forni.medical.model.dto.VisitCreationDTO;
import com.forni.medical.model.dto.VisitDTO;
import com.forni.medical.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/visits")
public class VisitController {

    private final VisitService visitService;

    @GetMapping
    public List<VisitDTO> getAllVisits(){
        return visitService.getAllVisits();
    }

    @GetMapping("/{id}")
    public VisitDTO getVisit(@PathVariable Long id){
        return visitService.findVisit(id);
    }

    @PostMapping
    public VisitDTO addVisit(@RequestBody VisitCreationDTO visitCreationDTO){
        return visitService.addVisit(visitCreationDTO);
    }

    @PatchMapping("/{id}/patient/{patientId}")
    public VisitDTO addPatientToVisit(@PathVariable Long id, @PathVariable Long patientId){
        return visitService.addPatientToVisit(id, patientId);
    }

    @DeleteMapping("/{id}")
    public void deleteVisit(@PathVariable Long id){
        visitService.deleteVisit(id);
    }
}
