package com.forni.medical.model.dto;

import jdk.dynalink.linker.LinkerServices;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacilityDTO {

    private String name;
    private String city;
    private String postCode;
    private String street;
    private String streetNumber;
}
