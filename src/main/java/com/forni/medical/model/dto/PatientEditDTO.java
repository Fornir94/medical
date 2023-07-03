package com.forni.medical.model.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientEditDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthday;
}