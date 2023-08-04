package com.forni.medical.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacilityCreationDTO {

    private String name;
    private String city;
    private String postCode;
    private String street;
    private String streetNumber;
}
