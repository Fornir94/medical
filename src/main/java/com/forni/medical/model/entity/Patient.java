package com.forni.medical.model.entity;

import com.forni.medical.model.dto.PatientEditDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Builder
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String idCardNo;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthday;
    @OneToMany(mappedBy = "patient")
    private List<Visit> visits;

    public void update(PatientEditDTO patientEditDTO){
        this.email = patientEditDTO.getEmail();
        this.password = patientEditDTO.getPassword();
        this.firstName = patientEditDTO.getFirstName();
        this.lastName = patientEditDTO.getLastName();
        this.phoneNumber = patientEditDTO.getPhoneNumber();
        this.birthday = patientEditDTO.getBirthday();
    }
}
