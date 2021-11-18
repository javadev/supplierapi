package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.OccupancyBasedPricingModel;
import com.cs.roomdbapi.model.OccupancyBasedPricingModelEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OccupancyBasedPricingModelMapper {

    OccupancyBasedPricingModelMapper MAPPER = Mappers.getMapper(OccupancyBasedPricingModelMapper.class);

    OccupancyBasedPricingModel toDTO(OccupancyBasedPricingModelEntity e);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    OccupancyBasedPricingModelEntity toEntity(OccupancyBasedPricingModel dto);

}
