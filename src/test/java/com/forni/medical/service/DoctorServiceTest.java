package com.forni.medical.service;

import com.forni.medical.exception.doctoreception.DoctorExistsException;
import com.forni.medical.exception.doctoreception.DoctorNotFoundException;
import com.forni.medical.exception.facilityexception.FacilitiesNotFoundException;
import com.forni.medical.mapper.DoctorMapper;
import com.forni.medical.mapper.FacilityMapper;
import com.forni.medical.mapper.PatientMapper;
import com.forni.medical.mapper.VisitMapper;
import com.forni.medical.model.dto.*;
import com.forni.medical.model.entity.Doctor;
import com.forni.medical.model.entity.Facility;
import com.forni.medical.model.entity.Patient;
import com.forni.medical.model.entity.Visit;
import com.forni.medical.repository.DoctorRepository;
import com.forni.medical.repository.FacilityRepository;
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
public class DoctorServiceTest {

    @Mock
    DoctorMapper doctorMapper;
    @Mock
    DoctorRepository doctorRepository;
    @Mock
    FacilityRepository facilityRepository;
    @Mock
    FacilityMapper facilityMapper;
    @Mock
    VisitMapper visitMapper;
    @Mock
    PatientMapper patientMapper;
    @InjectMocks
    DoctorService doctorService;

    @Test
    void addDoctor_DataCorrect_DoctorCreated() {
        DoctorCreationDTO doctorCreationDTO = new DoctorCreationDTO();
        doctorCreationDTO.setEmail("lol");
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setEmail("lol");
        Doctor doctor = new Doctor();
        doctor.setEmail("lol");
        Mockito.when(doctorRepository.findByEmail(eq(doctorCreationDTO.getEmail()))).thenReturn(Optional.empty());
        Mockito.when(doctorMapper.toEntity(eq(doctorCreationDTO))).thenReturn(doctor);
        Mockito.when(doctorRepository.save(eq(doctor))).thenReturn(doctor);
        Mockito.when(doctorMapper.toDto(eq(doctor))).thenReturn(doctorDTO);

        var result = doctorService.addDoctor(doctorCreationDTO);

        Assertions.assertEquals("lol", result.getEmail());
    }

    @Test
    void addDoctor_DoctorWithThisEmailExists_ExceptionThrow() {
        DoctorCreationDTO doctorCreationDTO = new DoctorCreationDTO();
        doctorCreationDTO.setEmail("lol");
        Mockito.when(doctorRepository.findByEmail(eq(doctorCreationDTO.getEmail()))).thenReturn(Optional.of(new Doctor()));

        var result = Assertions.assertThrows(DoctorExistsException.class, () -> doctorService.addDoctor(doctorCreationDTO));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getHttpStatus());
        Assertions.assertEquals("Doctor with this email already exists", result.getMessage());
    }

    @Test
    void getAllDoctors_DataCorrect_DoctorsReturned() {
        List<Doctor> doctors = new ArrayList<>();
        Doctor doctor = new Doctor();
        doctor.setEmail("lol");
        Doctor doctor1 = new Doctor();
        doctor1.setEmail("xd");
        doctors.add(doctor);
        doctors.add(doctor1);
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setEmail("lol");
        DoctorDTO doctorDTO1 = new DoctorDTO();
        doctorDTO1.setEmail("xd");
        Mockito.when(doctorRepository.findAll()).thenReturn(doctors);
        Mockito.when(doctorMapper.toDto(eq(doctor))).thenReturn(doctorDTO);
        Mockito.when(doctorMapper.toDto(eq(doctor1))).thenReturn(doctorDTO1);

        var result = doctorService.getAllDoctors();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("lol", result.get(0).getEmail());
        Assertions.assertEquals("xd", result.get(1).getEmail());
    }

    @Test
    void addDoctorToFacility_DataCorrect_DoctorAdd() {
        Facility facility = new Facility();
        facility.setId(1L);
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        List<Facility> facilities = new ArrayList<>();
        doctor.setFacilities(facilities);
        doctor.getFacilities().add(facility);
        FacilityDTO facilityDTO = new FacilityDTO();
        facilityDTO.setId(1L);
        Mockito.when(facilityRepository.findById(eq(1L))).thenReturn(Optional.of(facility));
        Mockito.when(doctorRepository.findById(eq(1L))).thenReturn(Optional.of(doctor));
        Mockito.when(doctorRepository.save(eq(doctor))).thenReturn(doctor);
        Mockito.when(facilityMapper.toDto(eq(facility))).thenReturn(facilityDTO);

        var result = doctorService.addDoctorToFacility(1L, 1L);

        Assertions.assertEquals(doctor.getFacilities().get(0).getId(), result.getId());
        Assertions.assertEquals(facilityDTO.getId(), result.getId());
    }

    @Test
    void addDoctorToFacility_FacilityNotFound_ExceptionThrow() {
        Facility facility = new Facility();
        facility.setId(1L);
        Mockito.when(facilityRepository.findById(eq(1L))).thenReturn(Optional.empty());

        var result = Assertions.assertThrows(FacilitiesNotFoundException.class, () -> doctorService.addDoctorToFacility(1L, 1L));

        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
        Assertions.assertEquals("Facility not found", result.getMessage());
    }

    @Test
    void addDoctorToFacility_DoctorNotFound_ExceptionThrow() {
        Facility facility = new Facility();
        facility.setId(1L);
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        Mockito.when(facilityRepository.findById(eq(1L))).thenReturn(Optional.of(facility));
        Mockito.when(doctorRepository.findById(eq(1L))).thenReturn(Optional.empty());

        var result = Assertions.assertThrows(DoctorNotFoundException.class, () -> doctorService.addDoctorToFacility(1L, 1L));

        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
        Assertions.assertEquals("Doctor not found", result.getMessage());
    }

    @Test
    void addDoctorToFacility_DoctorAlreadyAddToFacility_ExceptionThrow() {
        Facility facility = new Facility();
        facility.setId(1L);
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        List<Facility> facilities = new ArrayList<>();
        doctor.setFacilities(facilities);
        facilities.add(facility);
        Mockito.when(facilityRepository.findById(eq(1L))).thenReturn(Optional.of(facility));
        Mockito.when(doctorRepository.findById(eq(1L))).thenReturn(Optional.of(doctor));
        Mockito.when(doctorRepository.isDoctorAddToFacility(eq(1L), eq(1L))).thenReturn(true);

        var result = Assertions.assertThrows(DoctorExistsException.class, () -> doctorService.addDoctorToFacility(1L, 1L));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getHttpStatus());
        Assertions.assertEquals("Doctor in this facility already exists", result.getMessage());
    }

    @Test
    void getAllDoctorFacilities_DataCorrect_FacilitiesReturned() {
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        Facility facility = new Facility();
        facility.setName("lol");
        facility.setId(1L);
        List<Facility> facilities = new ArrayList<>();
        facilities.add(facility);
        FacilityDTO facilityDTO = new FacilityDTO();
        facilityDTO.setId(1L);
        facilityDTO.setName("lol");
        Mockito.when(doctorRepository.findById(eq(1L))).thenReturn(Optional.of(doctor));
        Mockito.when(doctorRepository.findFacilitiesByDoctorId(eq(1L))).thenReturn(facilities);
        Mockito.when(facilityMapper.toDto(eq(facility))).thenReturn(facilityDTO);

        var result = doctorService.getAllDoctorFacilities(1L);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("lol", result.get(0).getName());
    }

    @Test
    void getAllDoctorFacilities_DoctorNotFound_ExceptionThrow() {
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        Mockito.when(doctorRepository.findById(eq(1L))).thenReturn(Optional.empty());

        var result = Assertions.assertThrows(DoctorNotFoundException.class, () -> doctorService.getAllDoctorFacilities(1L));

        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
        Assertions.assertEquals("Doctor not found", result.getMessage());
    }

    @Test
    void getAllDoctorVisits_DataCorrect_VisitsReturned() {
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        Visit visit = new Visit();
        visit.setId(1L);
        visit.setVisitStartDate(LocalDateTime.of(2024, 12, 10, 17, 30, 0));
        List<Visit> visits = new ArrayList<>();
        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setId(1L);
        visitDTO.setVisitStartDate(LocalDateTime.of(2024, 12, 10, 17, 30, 0));
        visits.add(visit);
        Mockito.when(doctorRepository.findById(eq(1L))).thenReturn(Optional.of(doctor));
        Mockito.when(doctorRepository.findVisitsByDoctorId(eq(1L))).thenReturn(visits);
        Mockito.when(visitMapper.toDto(eq(visit))).thenReturn(visitDTO);

        var result = doctorService.getAllDoctorVisits(1L);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(LocalDateTime.of(2024, 12, 10, 17, 30, 0), result.get(0).getVisitStartDate());
    }

    @Test
    void getAllDoctorVisits_DoctorNotFound_ExceptionThrow() {
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        Mockito.when(doctorRepository.findById(eq(1L))).thenReturn(Optional.empty());

        var result = Assertions.assertThrows(DoctorNotFoundException.class, () -> doctorService.getAllDoctorFacilities(1L));

        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
        Assertions.assertEquals("Doctor not found", result.getMessage());
    }

    @Test
    void getAllDoctorPatients_DataCorrect_PatientsReturned() {
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        Visit visit = new Visit();
        visit.setId(1L);
        List<Visit> visits = new ArrayList<>();
        visits.add(visit);
        Patient patient = new Patient();
        patient.setEmail("lol");
        patient.setId(1L);
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setEmail("lol");
        patientDTO.setId(1L);
        visit.setPatient(patient);
        Mockito.when(doctorRepository.findById(eq(1L))).thenReturn(Optional.of(doctor));
        Mockito.when(doctorRepository.findVisitsByDoctorId(eq(1L))).thenReturn(visits);
        Mockito.when(patientMapper.toDto(eq(patient))).thenReturn(patientDTO);

        var result = doctorService.getAllDoctorPatients(1L);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("lol", result.get(0).getEmail());
    }

    @Test
    void getAllDoctorPatients_DoctorNotFound_ExceptionThrow() {
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        Mockito.when(doctorRepository.findById(eq(1L))).thenReturn(Optional.empty());

        var result = Assertions.assertThrows(DoctorNotFoundException.class, () -> doctorService.getAllDoctorPatients(1L));

        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
        Assertions.assertEquals("Doctor not found", result.getMessage());
    }
}
