package com.forni.medical.controller;

import com.forni.medical.model.dto.PatientCreationDTO;
import com.forni.medical.model.dto.PatientEditDTO;
import com.forni.medical.model.dto.PatientDTO;
import com.forni.medical.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public List<PatientDTO> getAllPatients(){
        return patientService.allPatients();
    }

    @GetMapping("/{email}")
    public PatientDTO getPatientByEmail(@PathVariable String email){
        return patientService.patientByEmail(email);
    }

    @PostMapping
    public PatientDTO addPatient(@RequestBody PatientCreationDTO patientCreationDTO){
        return patientService.addNewPatient(patientCreationDTO);
    }

    @DeleteMapping("/{email}")
    public void deletePatient(@PathVariable String email){
        patientService.deletePatient(email);
    }

    @PutMapping("/{email}")
    public PatientDTO updatePatient(@PathVariable String email, @RequestBody PatientEditDTO updatePatient){
       return patientService.update(email,updatePatient);
    }

    @PatchMapping("/{email}")
    public void updatePassword(@PathVariable String email, @RequestBody String password){
        patientService.updatePassword(email,password);
    }




}
