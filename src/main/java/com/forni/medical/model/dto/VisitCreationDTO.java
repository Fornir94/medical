package com.forni.medical.model.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitCreationDTO {

    private Long id;
    private LocalDateTime visitStartDate;
    private LocalDateTime visitEndDate;
    private PatientDTO patient;
}
