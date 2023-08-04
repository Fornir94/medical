package com.forni.medical.model.entity;

import com.forni.medical.model.Specialization;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Specialization specialization;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @ManyToMany
    @JoinTable(name = "doctor_facilities",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "facility_id"))
    private List<Facility> facilities;
    @OneToMany(mappedBy = "doctor")
    private List<Visit> visits;
}
