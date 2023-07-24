package com.forni.medical.service;

import com.forni.medical.exception.facilityexception.FacilityExistsException;
import com.forni.medical.mapper.FacilityMapper;
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

    public FacilityDTO addFacility(FacilityCreationDTO facilityCreationDTO) {
        Optional<Facility> facilityOptional = facilityRepository.findByfacilityName(facilityCreationDTO.getName());
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
}
