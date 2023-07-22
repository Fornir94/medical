package com.forni.medical.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.SpringApplication;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
    @JoinColumn(name="patient_id")
    private Patient patient;

    public void setDurationTime(){
        this.visitTime = Duration.between(visitStartDate, visitEndDate);
    }
}
