package com.forni.medical.service;

import com.forni.medical.exception.facilityexception.FacilitiesNotFoundException;
import com.forni.medical.exception.facilityexception.FacilityExistsException;
import com.forni.medical.mapper.DoctorMapper;
import com.forni.medical.mapper.FacilityMapper;
import com.forni.medical.model.dto.DoctorDTO;
import com.forni.medical.model.dto.FacilityCreationDTO;
import com.forni.medical.model.dto.FacilityDTO;
import com.forni.medical.model.entity.Facility;
import com.forni.medical.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacilityService {

    private final FacilityRepository facilityRepository;
    private final FacilityMapper facilityMapper;
    private final DoctorMapper doctorMapper;

    public FacilityDTO addFacility(FacilityCreationDTO facilityCreationDTO) {
        Optional<Facility> facilityOptional = facilityRepository.findByName(facilityCreationDTO.getName());
        if (facilityOptional.isPresent()) {
            throw new FacilityExistsException("Facility with this name already exists");
        }
        Facility facility = facilityMapper.toEntity(facilityCreationDTO);
        return facilityMapper.toDto(facilityRepository.save(facility));
    }

    public List<FacilityDTO> getAllFacility() {
        return facilityRepository.findAll().stream()
                .map(facilityMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<DoctorDTO> getAllFacilityDoctors(Long idFacility) {
        Facility facility = facilityRepository.findById(idFacility).orElseThrow(() -> new FacilitiesNotFoundException("Facility not found"));
        return facilityRepository.findDoctorsByFacilityId(facility.getId()).stream()
                .map(doctorMapper::toDto)
                .collect(Collectors.toList());
    }
}
