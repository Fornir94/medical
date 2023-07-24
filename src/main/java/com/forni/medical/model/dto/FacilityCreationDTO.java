package com.forni.medical.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacilityCreationDTO {

    private String facilityName;
    private String city;
    private String postCode;
    private String street;
    private String streetNumber;
}
