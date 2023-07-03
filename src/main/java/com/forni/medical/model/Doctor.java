package com.forni.medical.model;

import jakarta.persistence.Entity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Doctor {

    private String specialization;
    private String firstName;
    private String lastName;
    private String email;

}
