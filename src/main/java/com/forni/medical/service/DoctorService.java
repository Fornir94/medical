package com.forni.medical.service;

import com.forni.medical.exception.doctoreception.DoctorExistsException;
import com.forni.medical.exception.doctoreception.DoctorNotFoundException;
import com.forni.medical.exception.facilityexception.FacilityExistsException;
import com.forni.medical.mapper.DoctorMapper;
import com.forni.medical.mapper.FacilityMapper;
import com.forni.medical.model.dto.DoctorCreationDTO;
import com.forni.medical.model.dto.DoctorDTO;
import com.forni.medical.model.dto.FacilityDTO;
import com.forni.medical.model.entity.Doctor;
import com.forni.medical.model.entity.Facility;
import com.forni.medical.repository.DoctorRepository;
import com.forni.medical.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorMapper doctorMapper;
    private final DoctorRepository doctorRepository;
    private final FacilityRepository facilityRepository;
    private final FacilityMapper facilityMapper;

    public DoctorDTO addDoctor(DoctorCreationDTO doctorCreationDTO) {
        Optional<Doctor> doctorOptional = doctorRepository.findByEmail(doctorCreationDTO.getEmail());
        if (doctorOptional.isPresent()) {
            throw new DoctorExistsException("Doctor with this email already exists");
        }
        Doctor doctor = doctorMapper.toEntity(doctorCreationDTO);
        return doctorMapper.toDto(doctorRepository.save(doctor));
    }

    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(doctorMapper::toDto)
                .collect(Collectors.toList());
    }

    public FacilityDTO addDoctorToFacility(Long facilityId, Long doctorId) {
        Facility facility = facilityRepository.findById(facilityId).orElseThrow(() -> new FacilityExistsException("Facility not found"));
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));
        if (doctorRepository.isDoctorAddToFacility(doctorId, facilityId)) {
            throw new DoctorExistsException("Doctor in this facility already exists");
        }
        doctor.getFacilities().add(facility);
        doctorRepository.save(doctor);
        return facilityMapper.toDto(facility);
    }
}
