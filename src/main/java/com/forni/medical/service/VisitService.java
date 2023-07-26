package com.forni.medical.service;

import com.forni.medical.exception.doctoreception.DoctorExistsException;
import com.forni.medical.exception.doctoreception.DoctorNotFoundException;
import com.forni.medical.exception.patientexception.PatientNotFoundException;
import com.forni.medical.exception.visitexception.VisitDateException;
import com.forni.medical.exception.visitexception.VisitBookedException;
import com.forni.medical.exception.visitexception.VisitNotFoundException;
import com.forni.medical.mapper.VisitMapper;
import com.forni.medical.model.dto.VisitCreationDTO;
import com.forni.medical.model.dto.VisitDTO;
import com.forni.medical.model.entity.Doctor;
import com.forni.medical.model.entity.Patient;
import com.forni.medical.model.entity.Visit;
import com.forni.medical.repository.DoctorRepository;
import com.forni.medical.repository.PatientRepository;
import com.forni.medical.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public VisitDTO addVisit(VisitCreationDTO visitCreationDTO) {
        if (visitCreationDTO.getVisitStartDate().isBefore(LocalDateTime.now()) || visitCreationDTO.getVisitStartDate().getMinute() % 15 != 0) {
            throw new VisitDateException("Visit with this date is before then actual, or time is different than a full quarter of an hour");
        }
        List<Visit> checkVisit = visitRepository.findAllOverlapping(visitCreationDTO.getVisitStartDate(), visitCreationDTO.getVisitEndDate());
        if (!checkVisit.isEmpty()) {
            throw new VisitDateException("The time of the visit coincides with another visit");
        }
        Visit visit = visitMapper.toEntity(visitCreationDTO);
        visit.setDurationTime();
        return visitMapper.toDto(visitRepository.save(visit));
    }

    public List<VisitDTO> getAllVisits() {
        return visitRepository.findAll().stream()
                .map(visitMapper::toDto)
                .collect(Collectors.toList());
    }

    public VisitDTO addPatientToVisit(Long visitId, Long patientId) {
        Visit visit = visitRepository.findById(visitId).orElseThrow(() -> new VisitNotFoundException("Visit not found"));
        if (visit.getPatient() != null) {
            throw new VisitBookedException("This Visit is booked");
        }
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new PatientNotFoundException("Patient not found"));
        visit.setPatient(patient);
        return visitMapper.toDto(visitRepository.save(visit));
    }

    public VisitDTO addDoctorToVisit(Long visitId, Long doctorId) {
        Visit visit = visitRepository.findById(visitId).orElseThrow(() -> new VisitNotFoundException("Visit not found"));
        if (visit.getDoctor() != null) {
            throw new DoctorExistsException("The docotor has been already added");
        }
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));
        visit.setDoctor(doctor);
        return visitMapper.toDto(visitRepository.save(visit));
    }

    public VisitDTO findVisit(Long id) {
        return visitRepository.findById(id).stream()
                .map(visitMapper::toDto)
                .findFirst().orElseThrow(() -> new VisitNotFoundException("Visit not found"));
    }

    public void deleteVisit(Long id) {
        Visit visit = visitRepository.findById(id).orElseThrow(() -> new VisitNotFoundException("Visit not found"));
        visitRepository.delete(visit);
    }
}
