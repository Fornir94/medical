package com.forni.medical.service;

import com.forni.medical.exception.visitexception.VisitDateException;
import com.forni.medical.exception.visitexception.VisitExistsException;
import com.forni.medical.exception.visitexception.VisitFullException;
import com.forni.medical.exception.visitexception.VisitNotFoundException;
import com.forni.medical.mapper.VisitMapper;
import com.forni.medical.model.dto.VisitCreationDTO;
import com.forni.medical.model.dto.VisitDTO;
import com.forni.medical.model.entity.Patient;
import com.forni.medical.model.entity.Visit;
import com.forni.medical.repository.VisitRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class VisitServiceTest {

    @Mock
    VisitRepository visitRepository;
    @Mock
    VisitMapper visitMapper;
    @InjectMocks
    VisitService visitService;

    @Test
    void addVisit_DataCorrect_VisitCreated(){
        //Given
        VisitCreationDTO visitCreationDTO = new VisitCreationDTO();
        visitCreationDTO.setDate(LocalDateTime.of(2024, 12, 10, 17, 30, 00));
        Visit visit = new Visit();
        VisitDTO visitDTO = new VisitDTO();
        Mockito.when(visitRepository.findByDate(eq(visitCreationDTO.getDate()))).thenReturn(Optional.empty());
        Mockito.when(visitMapper.toEntity(eq(visitCreationDTO))).thenReturn(visit);
        Mockito.when(visitRepository.save(eq(visit))).thenReturn(visit);
        Mockito.when(visitMapper.toDto(eq(visit))).thenReturn(visitDTO);
        //When
        var reuslt=visitService.addVisit(visitCreationDTO);
        //Then
        Assertions.assertEquals(visitDTO, reuslt);
    }

    @Test
    void addVisit_VisitWithGivenDateExists_ExceptionThrow(){
        //Given
        VisitCreationDTO visitCreationDTO = new VisitCreationDTO();
        Mockito.when(visitRepository.findByDate(eq(visitCreationDTO.getDate()))).thenReturn(Optional.of(new Visit()));
        //When
        var result = Assertions.assertThrows(VisitExistsException.class, ()->visitService.addVisit(visitCreationDTO));
        //Then
        Assertions.assertEquals("Visit with this date already exists", result.getMessage());
        Assertions.assertEquals(HttpStatus.IM_USED, result.getHttpStatus());
    }

    @Test
    void addVisit_VisitDataCreationIsBeforeThenNow_ExceptionThrow(){
        //Given
        VisitCreationDTO visitCreationDTO = new VisitCreationDTO();
        visitCreationDTO.setDate(LocalDateTime.of(2022, 12, 10, 17, 30, 00));
        Mockito.when(visitRepository.findByDate(eq(visitCreationDTO.getDate()))).thenReturn(Optional.empty());
        //When
        var result = Assertions.assertThrows(VisitDateException.class, ()->visitService.addVisit(visitCreationDTO));
        //Then
        Assertions.assertEquals("Visit with this date is before then actual, or time is different than a full quarter of an hour", result.getMessage());
        Assertions.assertEquals(HttpStatus.FORBIDDEN, result.getHttpStatus());
    }

    @Test
    void addVisit_VisitDataCreationIsNotQuaterOfAnHour_ExceptionThrow(){
        //Given
        VisitCreationDTO visitCreationDTO = new VisitCreationDTO();
        visitCreationDTO.setDate(LocalDateTime.of(2024, 12, 10, 17, 25, 00));
        Mockito.when(visitRepository.findByDate(eq(visitCreationDTO.getDate()))).thenReturn(Optional.empty());
        //When
        var result = Assertions.assertThrows(VisitDateException.class, ()->visitService.addVisit(visitCreationDTO));
        //Then
        Assertions.assertEquals("Visit with this date is before then actual, or time is different than a full quarter of an hour", result.getMessage());
        Assertions.assertEquals(HttpStatus.FORBIDDEN, result.getHttpStatus());
    }

    @Test
    void getAllVisits_DataCorrect_ListOfVisitsCreated(){
        //Given
        List<Visit> visits = new ArrayList<>();
        Visit visit = new Visit();
        Visit visit1 = new Visit();
        visit.setDate(LocalDateTime.of(2024, 12, 10, 17, 30, 00));
        visit.setDate(LocalDateTime.of(2024, 12, 10, 18, 30, 00));
        visits.add(visit);
        visits.add(visit1);
        VisitDTO visitDTO = new VisitDTO();
        VisitDTO visitDTO1 = new VisitDTO();
        visitDTO.setDate(LocalDateTime.of(2024, 12, 10, 17, 30, 00));
        visitDTO1.setDate(LocalDateTime.of(2024, 12, 10, 18, 30, 00));
        Mockito.when(visitRepository.findAll()).thenReturn(visits);
        Mockito.when(visitMapper.toDto(eq(visit))).thenReturn(visitDTO);
        Mockito.when(visitMapper.toDto(eq(visit1))).thenReturn(visitDTO1);
        //When
        var result = visitService.getAllVisits();
        //Then
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(LocalDateTime.of(2024, 12, 10, 17, 30, 00), result.get(0).getDate());
        Assertions.assertEquals(LocalDateTime.of(2024, 12, 10, 18, 30, 00), result.get(1).getDate());
    }

    @Test
    void addPatientToVisit_DataCorrect_PatientAdd(){
        //Given
        Visit visit = new Visit();
        visit.setDate(LocalDateTime.of(2024, 12, 10, 17, 30, 00));
        visit.setId(1L);
        Patient patient = new Patient();
        patient.setId(1L);
        Mockito.when(visitRepository.findById(eq(visit.getId()))).thenReturn(Optional.of(visit));
        Mockito.when(visitRepository.save(visit)).thenReturn(visit);
        //When
        visitService.addPatientToVisit(1L, patient);
        //Then
        Mockito.verify(visitService, Mockito.times(1)).addPatientToVisit(eq(1L), eq(patient));
    }

    @Test
    void addPatientToVisit_VisitNotFound_ThrowException(){
        //Given
        Visit visit = new Visit();
        Patient patient = new Patient();
        patient.setId(1L);
        visit.setId(1L);
        Mockito.when(visitRepository.findById(eq(visit.getId()))).thenReturn(Optional.empty());
        //When
        var result = Assertions.assertThrows(VisitNotFoundException.class, ()->visitService.addPatientToVisit(1L, patient));
        //Then
        Assertions.assertEquals("Visit not found", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }

    @Test
    void addPatientToVisit_VisitIsFull_ThrowException(){
        //Given
        Visit visit = new Visit();
        Patient patient = new Patient();
        patient.setId(1L);
        visit.setId(1L);
        visit.setPatient(patient);
        Mockito.when(visitRepository.findById(eq(visit.getId()))).thenReturn(Optional.of(visit));
        //When
        var result = Assertions.assertThrows(VisitFullException.class, ()->visitService.addPatientToVisit(1L, patient));
        //Then
        Assertions.assertEquals("This Visit is full", result.getMessage());
        Assertions.assertEquals(HttpStatus.FORBIDDEN, result.getHttpStatus());
    }

    @Test
    void findVisit_DataCorrect_VisitFounded(){
        //Given
        Visit visit = new Visit();
        visit.setId(1L);
        visit.setDate(LocalDateTime.of(2024, 12, 10, 17, 30, 00));
        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setId(1L);
        visitDTO.setDate(LocalDateTime.of(2024, 12, 10, 17, 30, 00));
        Mockito.when(visitRepository.findById(eq(visit.getId()))).thenReturn(Optional.of(visit));
        Mockito.when(visitMapper.toDto(eq(visit))).thenReturn(visitDTO);
        //When
        var result = visitService.findVisit(1L);
        //Then
        Assertions.assertEquals(1L, visitDTO.getId());
        Assertions.assertEquals(LocalDateTime.of(2024, 12, 10, 17, 30, 00), visitDTO.getDate());
    }

    @Test
    void findVisit_VisitNotFound_ThrowException(){
        //Given
        Visit visit = new Visit();
        visit.setId(1L);
        Mockito.when(visitRepository.findById(eq(visit.getId()))).thenReturn(Optional.empty());
        //When
        var result = Assertions.assertThrows(VisitNotFoundException.class, ()->visitService.findVisit(visit.getId()));
        //Then
        Assertions.assertEquals("Visit not found", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }

    @Test
    void deleteVisit_DataCorrect_VisitDeleted(){
        //Given
        Visit visit = new Visit();
        visit.setId(1L);
        Mockito.when(visitRepository.findById(eq(visit.getId()))).thenReturn(Optional.of(visit));
        //When
        visitService.deleteVisit(1L);
        //Then
        Mockito.verify(visitRepository, Mockito.times(1)).delete(eq(visit));
    }

    @Test
    void deleteVisit_VisitNotFound_ThrowException(){
        //Given
        Visit visit = new Visit();
        visit.setId(1L);
        Mockito.when(visitRepository.findById(eq(visit.getId()))).thenReturn(Optional.empty());
        //When
        var result = Assertions.assertThrows(VisitNotFoundException.class, ()->visitService.deleteVisit(1L));
        //Then
        Assertions.assertEquals("Visit not found", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }
}
