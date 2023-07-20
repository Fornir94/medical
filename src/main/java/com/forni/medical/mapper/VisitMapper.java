package com.forni.medical.mapper;

import com.forni.medical.model.dto.VisitCreationDTO;
import com.forni.medical.model.dto.VisitDTO;
import com.forni.medical.model.entity.Visit;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder=@Builder(disableBuilder = true))
public interface VisitMapper {

    VisitDTO toDto(Visit visit);

    Visit toEntity(VisitCreationDTO visitCreationDTO);
}
