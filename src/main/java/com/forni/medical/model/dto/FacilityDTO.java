package com.forni.medical.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacilityDTO {

    private Long id;
    private String name;
    private String city;
    private String postCode;
    private String street;
    private String streetNumber;
    private List<Long> doctorsId;
}
