package com.forni.medical.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forni.medical.model.dto.FacilityCreationDTO;
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
public class FacilityControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllFacilities_DataCorrect_FacilitiesReturned() throws Exception {
        mockMvc.perform(get("/facilities"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].name").value("Palcowka lodz"));
    }

    @Test
    void getAllFacilityDoctor_DataCorrect_DoctorsReturned() throws Exception {
        mockMvc.perform(get("/facilities/1/doctors"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @Rollback
    void addFacility_DataCorrect_FacilityCreated() throws Exception {
        FacilityCreationDTO facilityCreationDTO = FacilityCreationDTO.builder()
                .name("LOLLOLLOL")
                .city("Łódź")
                .build();
        mockMvc.perform(post("/facilities")
                        .content(objectMapper.writeValueAsString(facilityCreationDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.name").value("LOLLOLLOL"))
                .andExpect(jsonPath("$.city").value("Łódź"));
    }
}
