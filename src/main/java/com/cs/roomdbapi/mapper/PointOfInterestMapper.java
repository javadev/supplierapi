package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.PointOfInterest;
import com.cs.roomdbapi.dto.PointOfInterestSaveRequest;
import com.cs.roomdbapi.model.PointOfInterestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PointOfInterestMapper {

    PointOfInterestMapper MAPPER = Mappers.getMapper(PointOfInterestMapper.class);

    @Mapping(target = "propertyId", source = "property.id")
    PointOfInterest toDTO(PointOfInterestEntity e);

    List<PointOfInterest> toListDTO(List<PointOfInterestEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "property", ignore = true)
    @Mapping(target = "categoryCode", ignore = true)
    @Mapping(target = "language", ignore = true)
    @Mapping(target = "descriptions", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    PointOfInterestEntity toEntity(PointOfInterestSaveRequest dto);
}
