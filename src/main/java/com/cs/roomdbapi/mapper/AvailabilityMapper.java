package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.Availability;
import com.cs.roomdbapi.model.AvailabilityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AvailabilityMapper {

    AvailabilityMapper MAPPER = Mappers.getMapper(AvailabilityMapper.class);

    Availability toDTO(AvailabilityEntity e);

    List<Availability> toListDTO(List<AvailabilityEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    AvailabilityEntity toEntity(Availability dto);

}
