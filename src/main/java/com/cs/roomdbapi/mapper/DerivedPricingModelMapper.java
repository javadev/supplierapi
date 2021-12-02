package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.DerivedPricingModel;
import com.cs.roomdbapi.model.DerivedPricingModelEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DerivedPricingModelMapper {

    DerivedPricingModelMapper MAPPER = Mappers.getMapper(DerivedPricingModelMapper.class);

    DerivedPricingModel toDTO(DerivedPricingModelEntity e);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    DerivedPricingModelEntity toEntity(DerivedPricingModel dto);

}
