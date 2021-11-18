package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.StandardPricingModel;
import com.cs.roomdbapi.model.StandardPricingModelEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StandardPricingModelMapper {

    StandardPricingModelMapper MAPPER = Mappers.getMapper(StandardPricingModelMapper.class);

    StandardPricingModel toDTO(StandardPricingModelEntity e);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    StandardPricingModelEntity toEntity(StandardPricingModel dto);

}
