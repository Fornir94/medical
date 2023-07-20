package com.forni.medical.controller;

import com.forni.medical.model.dto.VisitCreationDTO;
import com.forni.medical.model.dto.VisitDTO;
import com.forni.medical.model.entity.Patient;
import com.forni.medical.model.entity.Visit;
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

    @PatchMapping("/{id}")
    public void addPatientToVisit(@PathVariable Long id, @RequestBody Patient patient){
        visitService.addPatientToVisit(id, patient);
    }

    @DeleteMapping("/{id}")
    public void deleteVisit(@PathVariable Long id){
        visitService.deleteVisit(id);
    }
}
