package com.forni.medical.service;

import com.forni.medical.exception.doctoreception.DoctorExistsException;
import com.forni.medical.exception.doctoreception.DoctorNotFoundException;
import com.forni.medical.exception.patientexception.PatientNotFoundException;
import com.forni.medical.exception.visitexception.VisitDateException;
import com.forni.medical.exception.visitexception.VisitBookedException;
import com.forni.medical.exception.visitexception.VisitNotFoundException;
import com.forni.medical.mapper.VisitMapper;
import com.forni.medical.model.dto.DoctorDTO;
import com.forni.medical.model.dto.PatientDTO;
import com.forni.medical.model.dto.VisitCreationDTO;
import com.forni.medical.model.dto.VisitDTO;
import com.forni.medical.model.entity.Doctor;
import com.forni.medical.model.entity.Patient;
import com.forni.medical.model.entity.Visit;
import com.forni.medical.repository.DoctorRepository;
import com.forni.medical.repository.PatientRepository;
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
    @Mock
    PatientRepository patientRepository;
    @Mock
    DoctorRepository doctorRepository;

    @Test
    void addVisit_DataCorrect_VisitCreated() {
        //Given
        VisitCreationDTO visitCreationDTO = new VisitCreationDTO();
        visitCreationDTO.setVisitStartDate(LocalDateTime.of(2024, 12, 10, 17, 30, 0));
        visitCreationDTO.setVisitEndDate(LocalDateTime.of(2024, 12, 10, 18, 30, 0));
        Visit visit = new Visit();
        visit.setVisitStartDate(LocalDateTime.of(2024, 12, 10, 17, 30, 0));
        visit.setVisitEndDate(LocalDateTime.of(2024, 12, 10, 18, 30, 0));
        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setVisitStartDate(LocalDateTime.of(2024, 12, 10, 17, 30, 0));
        visitDTO.setVisitEndDate(LocalDateTime.of(2024, 12, 10, 18, 30, 0));
        List<Visit> visits = new ArrayList<>();
        Mockito.when(visitRepository.findAllOverlapping(eq(visitCreationDTO.getVisitStartDate()), eq(visitCreationDTO.getVisitEndDate()))).thenReturn(visits);
        Mockito.when(visitMapper.toEntity(eq(visitCreationDTO))).thenReturn(visit);
        Mockito.when(visitRepository.save(eq(visit))).thenReturn(visit);
        Mockito.when(visitMapper.toDto(eq(visit))).thenReturn(visitDTO);
        //When
        var result = visitService.addVisit(visitCreationDTO);
        //Then
        Assertions.assertEquals(visitDTO, result);
    }

    @Test
    void addVisit_VisitDataCreationIsNotQuaterOfAnHour_ExceptionThrow() {
        //Given
        VisitCreationDTO visitCreationDTO = new VisitCreationDTO();
        visitCreationDTO.setVisitStartDate(LocalDateTime.of(2024, 12, 10, 17, 25, 0));
        //When
        var result = Assertions.assertThrows(VisitDateException.class, () -> visitService.addVisit(visitCreationDTO));
        //Then
        Assertions.assertEquals("Visit with this date is before then actual, or time is different than a full quarter of an hour", result.getMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getHttpStatus());
    }

    @Test
    void addVisit_VisiTimeCoincidesWithAnotherOne_ExceptionThrow() {
        //Given
        VisitCreationDTO visitCreationDTO = new VisitCreationDTO();
        visitCreationDTO.setVisitStartDate(LocalDateTime.of(2024, 12, 10, 17, 30, 0));
        visitCreationDTO.setVisitEndDate(LocalDateTime.of(2024, 12, 10, 17, 45, 0));
        List<Visit> visits = new ArrayList<>();
        Visit visit = new Visit();
        visits.add(visit);
        visit.setVisitStartDate(LocalDateTime.of(2024, 12, 10, 17, 0, 0));
        visit.setVisitEndDate(LocalDateTime.of(2024, 12, 10, 18, 0, 0));
        Mockito.when(visitRepository.findAllOverlapping(eq(visitCreationDTO.getVisitStartDate()), eq(visitCreationDTO.getVisitEndDate()))).thenReturn(visits);
        //When
        var result = Assertions.assertThrows(VisitDateException.class, () -> visitService.addVisit(visitCreationDTO));
        //Then
        Assertions.assertEquals("The time of the visit coincides with another visit", result.getMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getHttpStatus());
    }

    @Test
    void getAllVisits_DataCorrect_ListOfVisitsCreated() {
        //Given
        List<Visit> visits = new ArrayList<>();
        Visit visit = new Visit();
        Visit visit1 = new Visit();
        visit.setVisitStartDate(LocalDateTime.of(2024, 12, 10, 17, 30, 0));
        visit.setVisitStartDate(LocalDateTime.of(2024, 12, 10, 18, 30, 0));
        visits.add(visit);
        visits.add(visit1);
        VisitDTO visitDTO = new VisitDTO();
        VisitDTO visitDTO1 = new VisitDTO();
        visitDTO.setVisitStartDate(LocalDateTime.of(2024, 12, 10, 17, 30, 0));
        visitDTO1.setVisitStartDate(LocalDateTime.of(2024, 12, 10, 18, 30, 0));
        Mockito.when(visitRepository.findAll()).thenReturn(visits);
        Mockito.when(visitMapper.toDto(eq(visit))).thenReturn(visitDTO);
        Mockito.when(visitMapper.toDto(eq(visit1))).thenReturn(visitDTO1);
        //When
        var result = visitService.getAllVisits();
        //Then
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(LocalDateTime.of(2024, 12, 10, 17, 30, 0), result.get(0).getVisitStartDate());
        Assertions.assertEquals(LocalDateTime.of(2024, 12, 10, 18, 30, 0), result.get(1).getVisitStartDate());
    }

    @Test
    void addPatientToVisit_DataCorrect_PatientAdd() {
        //Given
        Visit visit = new Visit();
        visit.setVisitStartDate(LocalDateTime.of(2024, 12, 10, 17, 30, 0));
        visit.setId(1L);
        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setVisitStartDate(LocalDateTime.of(2024, 12, 10, 17, 30, 0));
        visitDTO.setId(1L);
        Patient patient = new Patient();
        patient.setId(1L);
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(1L);
        Mockito.when(visitRepository.findById(eq(1L))).thenReturn(Optional.of(visit));
        Mockito.when(patientRepository.findById(eq(1L))).thenReturn(Optional.of(patient));
        Mockito.when(visitRepository.save(visit)).thenReturn(visit);
        Mockito.when(visitMapper.toDto(eq(visit))).thenReturn(visitDTO);
        //When
        var result = visitService.addPatientToVisit(1L, 1L);
        //Then
        Mockito.verify(visitRepository, Mockito.times(1)).save(eq(visit));
        Assertions.assertEquals(LocalDateTime.of(2024, 12, 10, 17, 30, 0), result.getVisitStartDate());
    }

    @Test
    void addPatientToVisit_VisitNotFound_ThrowException() {
        //Given
        Visit visit = new Visit();
        Patient patient = new Patient();
        patient.setId(1L);
        visit.setId(1L);
        Mockito.when(visitRepository.findById(eq(1L))).thenReturn(Optional.empty());
        //When
        var result = Assertions.assertThrows(VisitNotFoundException.class, () -> visitService.addPatientToVisit(1L, 1L));
        //Then
        Assertions.assertEquals("Visit not found", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }

    @Test
    void addPatientToVisit_VisitIsBooked_ThrowException() {
        //Given
        Visit visit = new Visit();
        Patient patient = new Patient();
        patient.setId(1L);
        visit.setId(1L);
        visit.setPatient(patient);
        Mockito.when(visitRepository.findById(eq(1L))).thenReturn(Optional.of(visit));
        //When
        var result = Assertions.assertThrows(VisitBookedException.class, () -> visitService.addPatientToVisit(1L, 1L));
        //Then
        Assertions.assertEquals("This Visit is booked", result.getMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getHttpStatus());
    }

    @Test
    void addPatientToVisit_PatientNotFound_ThrowException() {
        //Given
        Visit visit = new Visit();
        Patient patient = new Patient();
        patient.setId(1L);
        visit.setId(1L);
        Mockito.when(visitRepository.findById(eq(1L))).thenReturn(Optional.of(visit));
        Mockito.when(patientRepository.findById(eq(1L))).thenReturn(Optional.empty());
        //When
        var result = Assertions.assertThrows(PatientNotFoundException.class, () -> visitService.addPatientToVisit(1L, 1L));
        //Then
        Assertions.assertEquals("Patient not found", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }

    @Test
    void addDoctorToVisit_DataCorrect_DoctorAdd() {
        Visit visit = new Visit();
        visit.setVisitStartDate(LocalDateTime.of(2024, 12, 10, 17, 30, 0));
        visit.setId(1L);
        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setVisitStartDate(LocalDateTime.of(2024, 12, 10, 17, 30, 0));
        visitDTO.setId(1L);
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(1L);
        Mockito.when(visitRepository.findById(eq(1L))).thenReturn(Optional.of(visit));
        Mockito.when(doctorRepository.findById(eq(1L))).thenReturn(Optional.of(doctor));
        Mockito.when(visitRepository.save(visit)).thenReturn(visit);
        Mockito.when(visitMapper.toDto(eq(visit))).thenReturn(visitDTO);
        //When
        var result = visitService.addDoctorToVisit(1L, 1L);
        //Then
        Mockito.verify(visitRepository, Mockito.times(1)).save(eq(visit));
        Assertions.assertEquals(LocalDateTime.of(2024, 12, 10, 17, 30, 0), result.getVisitStartDate());
    }

    @Test
    void addDoctorToVisit_VisitNotFound_ThrowException() {
        //Given
        Visit visit = new Visit();
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        visit.setId(1L);
        Mockito.when(visitRepository.findById(eq(1L))).thenReturn(Optional.empty());
        //When
        var result = Assertions.assertThrows(VisitNotFoundException.class, () -> visitService.addPatientToVisit(1L, 1L));
        //Then
        Assertions.assertEquals("Visit not found", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }

    @Test
    void addDoctorToVisit_DoctorHasBeenAlreadyAdd_ThrowException() {
        //Given
        Visit visit = new Visit();
        Doctor doctor = new Doctor();
        visit.setId(1L);
        visit.setDoctor(doctor);
        Mockito.when(visitRepository.findById(eq(1L))).thenReturn(Optional.of(visit));
        //When
        var result = Assertions.assertThrows(DoctorExistsException.class, () -> visitService.addDoctorToVisit(1L, 1L));
        //Then
        Assertions.assertEquals("The docotor has been already added", result.getMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getHttpStatus());
    }

    @Test
    void addDoctorToVisit_DoctorNotFound_ThrowException() {
        //Given
        Visit visit = new Visit();
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        visit.setId(1L);
        Mockito.when(visitRepository.findById(eq(1L))).thenReturn(Optional.of(visit));
        Mockito.when(doctorRepository.findById(eq(1L))).thenReturn(Optional.empty());
        //When
        var result = Assertions.assertThrows(DoctorNotFoundException.class, () -> visitService.addDoctorToVisit(1L, 1L));
        //Then
        Assertions.assertEquals("Doctor not found", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }

    @Test
    void findVisit_DataCorrect_VisitFounded() {
        //Given
        Visit visit = new Visit();
        visit.setId(1L);
        visit.setVisitStartDate(LocalDateTime.of(2024, 12, 10, 17, 30, 0));
        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setId(1L);
        visitDTO.setVisitStartDate(LocalDateTime.of(2024, 12, 10, 17, 30, 0));
        Mockito.when(visitRepository.findById(eq(1L))).thenReturn(Optional.of(visit));
        Mockito.when(visitMapper.toDto(eq(visit))).thenReturn(visitDTO);
        //When
        var result = visitService.findVisit(1L);
        //Then
        Assertions.assertEquals(1L, visitDTO.getId());
        Assertions.assertEquals(LocalDateTime.of(2024, 12, 10, 17, 30, 0), visitDTO.getVisitStartDate());
    }

    @Test
    void findVisit_VisitNotFound_ThrowException() {
        //Given
        Visit visit = new Visit();
        visit.setId(1L);
        Mockito.when(visitRepository.findById(eq(1L))).thenReturn(Optional.empty());
        //When
        var result = Assertions.assertThrows(VisitNotFoundException.class, () -> visitService.findVisit(visit.getId()));
        //Then
        Assertions.assertEquals("Visit not found", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }

    @Test
    void deleteVisit_DataCorrect_VisitDeleted() {
        //Given
        Visit visit = new Visit();
        visit.setId(1L);
        Mockito.when(visitRepository.findById(eq(1L))).thenReturn(Optional.of(visit));
        //When
        visitService.deleteVisit(1L);
        //Then
        Mockito.verify(visitRepository, Mockito.times(1)).delete(eq(visit));
    }

    @Test
    void deleteVisit_VisitNotFound_ThrowException() {
        //Given
        Visit visit = new Visit();
        visit.setId(1L);
        Mockito.when(visitRepository.findById(eq(1L))).thenReturn(Optional.empty());
        //When
        var result = Assertions.assertThrows(VisitNotFoundException.class, () -> visitService.deleteVisit(1L));
        //Then
        Assertions.assertEquals("Visit not found", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }
}

