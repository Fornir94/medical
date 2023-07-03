package com.forni.medical.mapper;

import com.forni.medical.model.dto.PatientCreationDTO;
import com.forni.medical.model.dto.PatientDTO;
import com.forni.medical.model.entity.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {


    PatientDTO toDto (Patient patient);

    Patient toEntity(PatientCreationDTO patientCreationDTO);


}
