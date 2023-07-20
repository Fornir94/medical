package com.forni.medical.service;

import com.forni.medical.exception.patientexception.IllegalPatientDataException;
import com.forni.medical.exception.patientexception.PatientExistsException;
import com.forni.medical.exception.patientexception.PatientNotFoundException;
import com.forni.medical.mapper.PatientMapper;
import com.forni.medical.model.dto.PatientCreationDTO;
import com.forni.medical.model.dto.PatientDTO;
import com.forni.medical.model.dto.PatientEditDTO;
import com.forni.medical.model.entity.Patient;
import com.forni.medical.repository.PatientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    PatientRepository patientRepository;
    @Mock
    PatientMapper patientMapper;
    @InjectMocks
    PatientService patientService;

    @Test
    void patientByEmail_DataCorrect_PatientFound() {
        // Given
        Patient patient = new Patient();
        patient.setEmail("lol");
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setEmail("lol");
        Mockito.when(patientRepository.findByEmail(eq("lol"))).thenReturn(Optional.of(patient));
        Mockito.when(patientMapper.toDto(eq(patient))).thenReturn(patientDTO);
        //When
        var result = patientService.patientByEmail("lol");
        //Then
        Assertions.assertEquals("lol", result.getEmail());
    }

    @Test
    void patientByEmail_PatientWithGivenEmailDoesNotExist_ExceptionThrown() {
        // Given
        Patient patient = new Patient();
        patient.setEmail("lol");
        Mockito.when(patientRepository.findByEmail(eq("lol"))).thenReturn(Optional.empty());
        //When
        var result = Assertions.assertThrows(PatientNotFoundException.class, () -> patientService.patientByEmail(patient.getEmail()));
        //Then
        Assertions.assertEquals("Patient not found", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }

    @Test
    void addNewPatient_DataCorrect_PatientCreated() {
        //Given
        PatientCreationDTO patientCreationDTO = new PatientCreationDTO();
        Patient patient = new Patient();
        PatientDTO patientDTO = new PatientDTO();
        Mockito.when(patientRepository.findByEmail(eq(patientCreationDTO.getEmail()))).thenReturn(Optional.empty());
        Mockito.when(patientMapper.toEntity(eq(patientCreationDTO))).thenReturn(patient);
        Mockito.when(patientRepository.save(eq(patient))).thenReturn(patient);
        Mockito.when(patientMapper.toDto(eq(patient))).thenReturn(patientDTO);
        //When
        var result = patientService.addNewPatient(patientCreationDTO);
        //Then
        Assertions.assertEquals(patientDTO, result);
    }

    @Test
    void addNewPatient_PatientWithGivenEmailExists_ExceptionThrown() {
        //Given
        PatientCreationDTO patientCreationDTO = new PatientCreationDTO();
        Mockito.when(patientRepository.findByEmail(eq(patientCreationDTO.getEmail()))).thenReturn(Optional.of(new Patient()));
        //When
        var result = Assertions.assertThrows(PatientExistsException.class, () -> patientService.addNewPatient(patientCreationDTO));
        //Then
        Assertions.assertEquals("Patient with this email already exists", result.getMessage());
        Assertions.assertEquals(HttpStatus.IM_USED, result.getHttpStatus());
    }

    @Test
    void deletePatient_PatientWithGivenEmailDoesNotExist_ExceptionThrown() {
        //Given
        Patient patient = new Patient();
        patient.setEmail("lol");
        Mockito.when(patientRepository.findByEmail(eq(patient.getEmail()))).thenReturn(Optional.empty());
        //When
        var result = Assertions.assertThrows(PatientNotFoundException.class, () -> patientService.deletePatient(patient.getEmail()));
        //Then
        Assertions.assertEquals("Patient with this email does not exists", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }

    @Test
    void deletePatient_PatientExists_PatientDeleted() {
        //Given
        Patient patient = new Patient();
        patient.setEmail("lol");
        Mockito.when(patientRepository.findByEmail(eq(patient.getEmail()))).thenReturn(Optional.of(patient));
        //When
        patientService.deletePatient(patient.getEmail());
        //Then
        Mockito.verify(patientRepository, Mockito.times(1)).deleteByEmail(eq(patient.getEmail()));
    }

    @Test
    void allPatients_DataCorrect_ListOfPatiensCreated() {
        //Given
        List<Patient> patients = new ArrayList<>();
        Patient patient = new Patient();
        Patient patient1 = new Patient();
        patient.setEmail("loool");
        patient1.setEmail("xddd");
        patients.add(patient);
        patients.add(patient1);
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setEmail("loool");
        PatientDTO patientDTO1 = new PatientDTO();
        patientDTO1.setEmail("xddd");
        Mockito.when(patientRepository.findAll()).thenReturn(patients);
        Mockito.when(patientMapper.toDto(eq(patient))).thenReturn(patientDTO);
        Mockito.when(patientMapper.toDto(eq(patient1))).thenReturn(patientDTO1);
        //When
        var result = patientService.allPatients();
        //Then
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("loool", result.get(0).getEmail());
        Assertions.assertEquals("xddd", result.get(1).getEmail());
    }

    @Test
    void updatePassword_DataCorrect_PasswordUpdated() {
        //Given
        Patient patient = new Patient();
        patient.setEmail("lol");
        patient.setPassword("1234");
        Mockito.when(patientRepository.findByEmail(eq(patient.getEmail()))).thenReturn(Optional.of(patient));
        Mockito.when(patientRepository.save(eq(patient))).thenReturn(patient);
        //When
        patientService.updatePassword("lol", "4321");
        //Then
        Assertions.assertEquals("4321", patient.getPassword());
    }

    @Test
    void updatePassword_PatientWithGivenEmailDoesNotExist_ExceptionThrown() {
        //Given
        Patient patient = new Patient();
        patient.setEmail("lol");
        Mockito.when(patientRepository.findByEmail(eq(patient.getEmail()))).thenReturn(Optional.empty());
        //When
        var result = Assertions.assertThrows(PatientNotFoundException.class, () -> patientService.updatePassword(patient.getEmail(), patient.getPassword()));
        //Then
        Assertions.assertEquals("Patient not found", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }

    @Test
    void updatePassword_NewPasswordIsNull_ExceptionThrown() {
        //Given
        Patient patient = new Patient();
        patient.setEmail("lol");
        Mockito.when(patientRepository.findByEmail(eq(patient.getEmail()))).thenReturn(Optional.of(patient));
        //When
        var result = Assertions.assertThrows(IllegalArgumentException.class, () -> patientService.updatePassword(patient.getEmail(), null));
        //Then
        Assertions.assertEquals("Wrong password", result.getMessage());
    }

    @Test
    void update_DataCorrectWithDifferentUpdateEmail_PatientUpdated() {
        //Given
        Patient patient = new Patient();
        patient.setEmail("lol");
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setEmail("xdd");
        PatientEditDTO patientEditDTO = new PatientEditDTO();
        patientEditDTO.setEmail("xdd");
        patientEditDTO.setPassword("1234");
        patientEditDTO.setBirthday(LocalDate.EPOCH);
        patientEditDTO.setFirstName("adda");
        patientEditDTO.setLastName("jakaa");
        patientEditDTO.setPhoneNumber("4233222");
        Mockito.when(patientRepository.findByEmail(eq("lol"))).thenReturn(Optional.of(patient));
        Mockito.when(patientRepository.findByEmail(eq("xdd"))).thenReturn(Optional.empty());
        Mockito.when(patientRepository.save(eq(patient))).thenReturn(patient);
        Mockito.when(patientMapper.toDto(eq(patient))).thenReturn(patientDTO);
        //When
        PatientDTO result = patientService.update("lol", patientEditDTO);
        //Then
        Mockito.verify(patientRepository, Mockito.times(1)).save(eq(patient));
        Assertions.assertEquals("xdd", result.getEmail());
    }

    @Test
    void update_DataCorrectWithTheSameUpdateEmail_PatientUpdated() {
        //Given
        Patient patient = new Patient();
        patient.setEmail("lol");
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setEmail("lol");
        PatientEditDTO patientEditDTO = new PatientEditDTO();
        patientEditDTO.setEmail("lol");
        patientEditDTO.setPassword("1234");
        patientEditDTO.setBirthday(LocalDate.EPOCH);
        patientEditDTO.setFirstName("adda");
        patientEditDTO.setLastName("jakaa");
        patientEditDTO.setPhoneNumber("4233222");
        Mockito.when(patientRepository.findByEmail(eq("lol"))).thenReturn(Optional.of(patient));
        Mockito.when(patientRepository.save(eq(patient))).thenReturn(patient);
        Mockito.when(patientMapper.toDto(eq(patient))).thenReturn(patientDTO);
        //When
        PatientDTO result = patientService.update("lol", patientEditDTO);
        //Then
        Mockito.verify(patientRepository, Mockito.times(1)).save(eq(patient));
        Assertions.assertEquals("lol", result.getEmail());
    }

    @Test
    void update_PatientWithGivenEmailDoesNotExist_ExceptionThrown() {
        //Given
        Patient patient = new Patient();
        patient.setEmail("lol");
        PatientEditDTO patientEditDTO = new PatientEditDTO();
        Mockito.when(patientRepository.findByEmail(eq(patient.getEmail()))).thenReturn(Optional.empty());
        //When
        var result = Assertions.assertThrows(PatientNotFoundException.class, () -> patientService.update("lol", patientEditDTO));
        //Then
        Assertions.assertEquals("Patient not found", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }

    @Test
    void update_PatientWithEditEmailAlreadyExistsAndEditEmailIsDifferentThenEditPatient_ExceptionThrown() {
        //Given
        Patient patient = new Patient();
        patient.setEmail("lol");
        Patient patient1 = new Patient();
        patient1.setEmail("xdd");
        PatientEditDTO patientEditDTO = new PatientEditDTO();
        patientEditDTO.setEmail("xdd");
        Mockito.when(patientRepository.findByEmail(eq(patient.getEmail()))).thenReturn(Optional.of(patient));
        Mockito.when(patientRepository.findByEmail(eq(patientEditDTO.getEmail()))).thenReturn(Optional.of(patient1));
        //When
        var result = Assertions.assertThrows(PatientExistsException.class, () -> patientService.update("lol", patientEditDTO));
        //Then
        Assertions.assertEquals("Any user have a such email", result.getMessage());
        Assertions.assertEquals(HttpStatus.IM_USED, result.getHttpStatus());
    }

    @Test
    void update_EditPatientDataWithNull_ExceptionThrown() {
        //Given
        Patient patient = new Patient();
        patient.setEmail("lol");
        PatientEditDTO patientEditDTO = new PatientEditDTO();
        patientEditDTO.setEmail("lol");
        Mockito.when(patientRepository.findByEmail(eq(patient.getEmail()))).thenReturn(Optional.of(patient));
        //When
        var result = Assertions.assertThrows(IllegalPatientDataException.class, () -> patientService.update("lol", patientEditDTO));
        //Then
        Assertions.assertEquals("Changing ID card number, or add null  is not allowed!", result.getMessage());
        Assertions.assertEquals(HttpStatus.FORBIDDEN, result.getHttpStatus());
    }
}
