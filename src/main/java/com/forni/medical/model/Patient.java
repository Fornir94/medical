package com.forni.medical.model;

import com.forni.medical.dto.PatientEditDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Entity
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

    public void update(PatientEditDTO patientEditDTO){
        this.email = patientEditDTO.getEmail();
        this.password = patientEditDTO.getPassword();
        this.firstName = patientEditDTO.getFirstName();
        this.lastName = patientEditDTO.getLastName();
        this.phoneNumber = patientEditDTO.getPhoneNumber();
        this.birthday = patientEditDTO.getBirthday();
    }
}
