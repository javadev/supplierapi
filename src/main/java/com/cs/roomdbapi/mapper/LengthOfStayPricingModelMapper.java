package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.LengthOfStayPricingModel;
import com.cs.roomdbapi.model.LengthOfStayPricingModelEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LengthOfStayPricingModelMapper {

    LengthOfStayPricingModelMapper MAPPER = Mappers.getMapper(LengthOfStayPricingModelMapper.class);

    LengthOfStayPricingModel toDTO(LengthOfStayPricingModelEntity e);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    LengthOfStayPricingModelEntity toEntity(LengthOfStayPricingModel dto);

}
