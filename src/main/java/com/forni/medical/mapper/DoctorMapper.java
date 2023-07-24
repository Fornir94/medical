package com.forni.medical.mapper;

import com.forni.medical.model.dto.DoctorCreationDTO;
import com.forni.medical.model.dto.DoctorDTO;
import com.forni.medical.model.entity.Doctor;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface DoctorMapper {

    DoctorDTO toDto(Doctor doctor);

    Doctor toEntity(DoctorCreationDTO doctorCreationDTO);
}
