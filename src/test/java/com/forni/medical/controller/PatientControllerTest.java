package com.forni.medical.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forni.medical.model.dto.PatientCreationDTO;
import com.forni.medical.model.dto.PatientEditDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getPatientByEmail_PatientExists_PatientReturned() throws Exception {
        mockMvc.perform(get("/patients/misiakasia@gmail.com"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.email").value("misiakasia@gmail.com"))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    void getAllPatient_DataCorrect_PatientsReturned() throws Exception {
        mockMvc.perform(get("/patients"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(7)))
                .andExpect(jsonPath("$[0].email").value("misiakasia@gmail.com"));
    }

    @Test
    void getPatientsVisits_VisitsExists_VisitsReturned() throws Exception {
        mockMvc.perform(get("/patients/misiakasia@gmail.com/visits"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @Rollback
    void addPatient_DataCorrect_PatientAddToBase() throws Exception {
        PatientCreationDTO patientCreationDTO = PatientCreationDTO.builder()
                .email("xdd@hot.mail")
                .password("1234")
                .build();
        mockMvc.perform(post("/patients")
                        .content(objectMapper.writeValueAsString(patientCreationDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.email").value("xdd@hot.mail"));
    }

    @Test
    @Rollback
    void updatePassword_PatientExists_PasswordUpdated() throws Exception {
        mockMvc.perform(patch("/patients/misiakasia@gmail.com")
                        .content("4321")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    @Rollback
    void updatePatient_PatientExists_PatientUpdated() throws Exception {
        PatientEditDTO patientEditDTO = PatientEditDTO.builder()
                .firstName("Jaca")
                .lastName("Kowalski")
                .phoneNumber("1234456")
                .birthday(LocalDate.EPOCH)
                .password("987765")
                .email("kqqaa@gmail.com")
                .build();
        mockMvc.perform(put("/patients/walezgwinta@gmail.com")
                        .content(objectMapper.writeValueAsString(patientEditDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.firstName").value("Jaca"))
                .andExpect(jsonPath("$.lastName").value("Kowalski"));
    }

    @Test
    @Rollback
    void deletePatient_PatientExists_PatientDeleted() throws Exception {
        mockMvc.perform(delete("/patients/wisimulacha@gmail.com"))
                .andDo(print())
                .andExpect(status().is(200));
    }
}
