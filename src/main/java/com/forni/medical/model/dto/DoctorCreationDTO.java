package com.forni.medical.model.dto;

import com.forni.medical.model.Specialization;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorCreationDTO {

    private Specialization specialization;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
