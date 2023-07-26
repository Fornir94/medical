package com.forni.medical.mapper;

import com.forni.medical.model.dto.FacilityCreationDTO;
import com.forni.medical.model.dto.FacilityDTO;
import com.forni.medical.model.entity.Doctor;
import com.forni.medical.model.entity.Facility;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface FacilityMapper {

    @Mapping(target = "doctorsId", source = "doctors", qualifiedByName = "mapDoctors")
    FacilityDTO toDto(Facility facility);

    Facility toEntity(FacilityCreationDTO facilityCreationDTO);

    @Named("mapDoctors")
    default List<Long> mapDoctorsToIds(List<Doctor> doctors) {
        if (doctors == null) {
            return null;
        }
        return doctors.stream()
                .map(Doctor::getId)
                .collect(Collectors.toList());
    }
}
