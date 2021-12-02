package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.PricingModel;
import com.cs.roomdbapi.dto.PricingModelSaveRequest;
import com.cs.roomdbapi.model.PricingModelEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PricingModelMapper {

    PricingModelMapper MAPPER = Mappers.getMapper(PricingModelMapper.class);

    @Mapping(target = "propertyId", source = "property.id")
    @Mapping(target = "standardPricingModel", ignore = true)
    @Mapping(target = "derivedPricingModel", ignore = true)
    @Mapping(target = "occupancyBasedPricingModel", ignore = true)
    @Mapping(target = "lengthOfStayPricingModel", ignore = true)
    PricingModel toDTO(PricingModelEntity e);

    List<PricingModel> toListDTO(List<PricingModelEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "property", ignore = true)
    @Mapping(target = "pricingModelType", ignore = true)
    @Mapping(target = "recordId", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    PricingModelEntity toEntity(PricingModelSaveRequest dto);

}
