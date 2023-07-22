package com.forni.medical.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VisitDTO {
    private Long id;
    private LocalDateTime visitStartDate;
    private LocalDateTime visitEndDate;
    private Duration durationTime;
    private PatientDTO patient;
    private Long patientId;
}
