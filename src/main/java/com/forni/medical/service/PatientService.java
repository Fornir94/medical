package com.forni.medical.service;

import com.forni.medical.mapper.VisitMapper;
import com.forni.medical.model.dto.PatientCreationDTO;
import com.forni.medical.model.dto.PatientEditDTO;
import com.forni.medical.exception.patientexception.PatientExistsException;
import com.forni.medical.exception.patientexception.IllegalPatientDataException;
import com.forni.medical.exception.patientexception.PatientNotFoundException;
import com.forni.medical.mapper.PatientMapper;
import com.forni.medical.model.dto.VisitDTO;
import com.forni.medical.model.entity.Patient;
import com.forni.medical.model.dto.PatientDTO;
import com.forni.medical.model.entity.Visit;
import com.forni.medical.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final VisitMapper visitMapper;

    public PatientDTO patientByEmail(String email) {
        return patientRepository.findByEmail(email).stream()
                .map(patientMapper::toDto)
                .findFirst().orElseThrow(() -> new PatientNotFoundException("Patient not found"));
    }

    public PatientDTO update(String email, PatientEditDTO updatePatient) {
        Patient patient = patientRepository.findByEmail(email).orElseThrow(() -> new PatientNotFoundException("Patient not found"));
        Optional<Patient> patient1 = patientRepository.findByEmail(updatePatient.getEmail());

        if (!updatePatient.getEmail().equals(email) && patient1.isPresent()) {
            throw new PatientExistsException("Any user have a such email");
        }
        if (updatePatient.getBirthday() == null || updatePatient.getEmail() == null
                || updatePatient.getPassword() == null || updatePatient.getFirstName() == null || updatePatient.getLastName() == null
                || updatePatient.getPhoneNumber() == null) {
            throw new IllegalPatientDataException("Changing ID card number, or add null  is not allowed!");
        }

        patient.update(updatePatient);
        return patientMapper.toDto(patientRepository.save(patient));
    }

    public void updatePassword(String email, String password) {
        Patient patient = patientRepository.findByEmail(email).orElseThrow(() -> new PatientNotFoundException("Patient not found"));
        if (password == null) {
            throw new IllegalArgumentException("Wrong password");
        }
        patient.setPassword(password);
        patientRepository.save(patient);
    }

    public List<PatientDTO> allPatients() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<VisitDTO> allPatientVisits(String email){
        Patient patient = patientRepository.findByEmail(email).orElseThrow(()->new PatientNotFoundException("Patient not found"));
        return patient.getVisits().stream()
                .map(visitMapper::toDto)
                .collect(Collectors.toList());
    }

    public PatientDTO addNewPatient(PatientCreationDTO patientCreationDTO) {
        Optional<Patient> patient1 = patientRepository.findByEmail(patientCreationDTO.getEmail());
        if (patient1.isPresent()) {
            throw new PatientExistsException("Patient with this email already exists");
        }
        Patient patient = patientMapper.toEntity(patientCreationDTO);
         return patientMapper.toDto(patientRepository.save(patient));
    }

    public void deletePatient(String email) {
        patientRepository.findByEmail(email).orElseThrow(() -> new PatientNotFoundException("Patient with this email does not exists"));
        patientRepository.deleteByEmail(email);
    }
}
