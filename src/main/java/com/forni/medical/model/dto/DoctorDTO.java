package com.forni.medical.model.dto;

import com.forni.medical.model.Specialization;
import com.forni.medical.model.entity.Facility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {

    private Long id;
    private Specialization specialization;
    private String firstName;
    private String lastName;
    private String email;
    private List<Long> facilityId;
}
