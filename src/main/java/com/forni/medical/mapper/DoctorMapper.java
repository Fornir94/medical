package com.forni.medical.mapper;

import com.forni.medical.model.dto.DoctorCreationDTO;
import com.forni.medical.model.dto.DoctorDTO;
import com.forni.medical.model.entity.Doctor;
import com.forni.medical.model.entity.Facility;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface DoctorMapper {

    @Mapping(target = "facilityId", source = "facilities", qualifiedByName = "mapFacilities")
    DoctorDTO toDto(Doctor doctor);

    Doctor toEntity(DoctorCreationDTO doctorCreationDTO);

    @Named("mapFacilities")
    default List<Long> mapFacilitiesToIds(List<Facility> facilities) {
        if (facilities == null) {
            return null;
        }
        return facilities.stream()
                .map(Facility::getId)
                .collect(Collectors.toList());
    }
}
