package com.forni.medical.mapper;

import com.forni.medical.model.dto.FacilityCreationDTO;
import com.forni.medical.model.dto.FacilityDTO;
import com.forni.medical.model.entity.Facility;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder=@Builder(disableBuilder = true))
public interface FacilityMapper {

    FacilityDTO toDto(Facility facility);

    Facility toEntity(FacilityCreationDTO facilityCreationDTO);
}
