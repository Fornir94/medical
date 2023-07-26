package com.forni.medical.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forni.medical.model.Specialization;
import com.forni.medical.model.dto.DoctorCreationDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllDoctors_DataCorrect_DoctorsReturned() throws Exception {
        mockMvc.perform(get("/doctors"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].email").value("jj@lol.pl"));
    }

    @Test
    void getAllDoctorPatients_DataCorrect_PatientsReturned() throws Exception {
        mockMvc.perform(get("/doctors/1/patients"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getDoctorFacilities_DataCorrect_FacilitiesReturned() throws Exception {
        mockMvc.perform(get("/doctors/1/facilities"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getDoctorVisits_DataCorrect_VisitsReturned() throws Exception {
        mockMvc.perform(get("/doctors/1/visits"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @Rollback
    void addDoctor_DataCorrect_DoctorCreated() throws Exception {
        DoctorCreationDTO doctorCreationDTO = DoctorCreationDTO.builder()
                .email("lol")
                .password("1234")
                .specialization(Specialization.SURGEON)
                .build();
        mockMvc.perform(post("/doctors")
                        .content(objectMapper.writeValueAsString(doctorCreationDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.email").value("lol"));
    }

    @Test
    @Rollback
    void addDoctorToFacility_DataCorrect_FacilityAdd() throws Exception {
        mockMvc.perform(patch("/doctors/1/facility/1"))
                .andDo(print())
                .andExpect(status().is(200));
    }
}
