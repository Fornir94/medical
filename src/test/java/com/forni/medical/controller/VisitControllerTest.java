package com.forni.medical.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forni.medical.model.dto.VisitCreationDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class VisitControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private VisitController visitController;

    @Test
    void getAllVisit_DataCorrect_VisitsReturned() throws Exception {
        mockMvc.perform(get("/visits"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].visitDate").value("2024-07-20T17:30:00"));
    }

    @Test
    void getVisit_VisitExists_VisitReturned() throws Exception {
        mockMvc.perform(get("/visits/1"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.visitDate").value("2024-07-20T17:30:00"));
    }

    @Test
    @Rollback
    void addVisit_DataCorrect_VisitCreated() throws Exception {
        VisitCreationDTO visitCreationDTO = VisitCreationDTO.builder()
                .visitStartDate(LocalDateTime.of(2025, 12, 10, 17, 30, 0))
                .visitEndDate(LocalDateTime.of(2025, 12, 10, 18, 30, 0))
                .build();
        mockMvc.perform(post("/visits")
                        .content(objectMapper.writeValueAsString(visitCreationDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.visitStartDate").value("2025-12-10T17:30:00"))
                .andExpect(jsonPath("$.visitEndDate").value("2025-12-10T18:30:00"));
    }

    @Test
    @Rollback
    void addPatientToVisit_DataCorrect_PatientAdd() throws Exception{
        mockMvc.perform(patch("/visits/1/patient/1"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.patientId").value(1L));
    }

    @Test
    @Rollback
    void deleteVisit_DataCorrect_VisitDeleted() throws Exception{
        mockMvc.perform(delete("/visits/1"))
                .andDo(print())
                .andExpect(status().is(200));
    }
}
