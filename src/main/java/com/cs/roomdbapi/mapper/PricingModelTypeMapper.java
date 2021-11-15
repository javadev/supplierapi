package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.PricingModelType;
import com.cs.roomdbapi.model.PricingModelTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PricingModelTypeMapper {

    PricingModelTypeMapper MAPPER = Mappers.getMapper(PricingModelTypeMapper.class);

    PricingModelType toDTO(PricingModelTypeEntity e);

    List<PricingModelType> toListDTO(List<PricingModelTypeEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tableName", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    PricingModelTypeEntity toEntity(PricingModelType dto);

}
