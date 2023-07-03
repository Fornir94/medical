package com.forni.medical.mapper;

import com.forni.medical.dto.PatientCreationDTO;
import com.forni.medical.dto.PatientDTO;
import com.forni.medical.dto.PatientEditDTO;
import com.forni.medical.dto.PatientPasswordEditDTO;
import com.forni.medical.model.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {


    PatientDTO toDto (Patient patient);

    Patient toEntity(PatientCreationDTO patientCreationDTO);


}
