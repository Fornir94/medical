package com.forni.medical.service;

import com.forni.medical.exception.facilityexception.FacilityExistsException;
import com.forni.medical.mapper.FacilityMapper;
import com.forni.medical.model.dto.FacilityCreationDTO;
import com.forni.medical.model.dto.FacilityDTO;
import com.forni.medical.model.entity.Facility;
import com.forni.medical.repository.FacilityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class FacilityServiceTest {

    @Mock
    FacilityMapper facilityMapper;
    @Mock
    FacilityRepository facilityRepository;
    @InjectMocks
    FacilityService facilityService;

    @Test
    void addFacility_DaraCorrect_FacilityCreated() {
        //Given
        FacilityCreationDTO facilityCreationDTO = new FacilityCreationDTO();
        facilityCreationDTO.setName("lol");
        FacilityDTO facilityDTO = new FacilityDTO();
        facilityDTO.setName("lol");
        Facility facility = new Facility();
        facility.setName("lol");
        Mockito.when(facilityRepository.findByfacilityName(eq(facilityCreationDTO.getName()))).thenReturn(Optional.empty());
        Mockito.when(facilityMapper.toEntity(eq(facilityCreationDTO))).thenReturn(facility);
        Mockito.when(facilityRepository.save(eq(facility))).thenReturn(facility);
        Mockito.when(facilityMapper.toDto(eq(facility))).thenReturn(facilityDTO);
        //When
        var result = facilityService.addFacility(facilityCreationDTO);
        //Then
        Assertions.assertEquals(facilityDTO, result);
    }

    @Test
    void addFacility_FacilityWithThisNameExists_ExceptionThrow() {
        //Given
        FacilityCreationDTO facilityCreationDTO = new FacilityCreationDTO();
        facilityCreationDTO.setName("lol");
        Mockito.when(facilityRepository.findByfacilityName(eq(facilityCreationDTO.getName()))).thenReturn(Optional.of(new Facility()));
        //When
        var result = Assertions.assertThrows(FacilityExistsException.class, () -> facilityService.addFacility(facilityCreationDTO));
        //Then
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getHttpStatus());
        Assertions.assertEquals("Facility with this name already exists", result.getMessage());
    }

    @Test
    void getAllFacility_DataCorrect_FacilitiesReturned() {
        //Given
        List<Facility> facilityList = new ArrayList<>();
        Facility facility = new Facility();
        facility.setName("lol");
        Facility facility1 = new Facility();
        facility1.setName("xd");
        facilityList.add(facility1);
        facilityList.add(facility);
        FacilityDTO facilityDTO = new FacilityDTO();
        facilityDTO.setName("lol");
        FacilityDTO facilityDTO1 = new FacilityDTO();
        facilityDTO1.setName("xd");
        Mockito.when(facilityRepository.findAll()).thenReturn(facilityList);
        Mockito.when(facilityMapper.toDto(eq(facility))).thenReturn(facilityDTO);
        Mockito.when(facilityMapper.toDto(eq(facility1))).thenReturn(facilityDTO1);
        //When
        var result = facilityService.getAllFacility();
        //Then
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("xd", result.get(0).getName());
        Assertions.assertEquals("lol", result.get(1).getName());
    }
}
