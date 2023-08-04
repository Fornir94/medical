package com.forni.medical.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Builder
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime visitStartDate;
    private LocalDateTime visitEndDate;
    private Duration visitTime;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    public void setDurationTime() {
        this.visitTime = Duration.between(visitStartDate, visitEndDate);
    }
}
