package com.forni.medical.mapper;

import com.forni.medical.model.dto.PatientCreationDTO;
import com.forni.medical.model.dto.PatientDTO;
import com.forni.medical.model.entity.Patient;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder=@Builder(disableBuilder = true))
public interface PatientMapper {

    PatientDTO toDto (Patient patient);

    Patient toEntity(PatientCreationDTO patientCreationDTO);


}
