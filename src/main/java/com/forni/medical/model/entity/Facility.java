package com.forni.medical.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Builder
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String facilityName;
    private String city;
    private String postCode;
    private String street;
    private String streetNumber;
    @ManyToMany(mappedBy = "facilities")
    private List<Doctor> doctors;
}
