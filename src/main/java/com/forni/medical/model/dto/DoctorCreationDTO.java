package com.forni.medical.model.dto;

import com.forni.medical.model.Specialization;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorCreationDTO {

    private Specialization specialization;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
