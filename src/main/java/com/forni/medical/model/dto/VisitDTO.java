package com.forni.medical.model.dto;

import com.forni.medical.model.entity.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VisitDTO {
    private Long id;
    private LocalDateTime date;
}
