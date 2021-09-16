package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.SellableUnitType;
import com.cs.roomdbapi.model.SellableUnitTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SellableUnitTypeMapper {

    SellableUnitTypeMapper MAPPER = Mappers.getMapper(SellableUnitTypeMapper.class);

    SellableUnitType toDTO(SellableUnitTypeEntity e);

    List<SellableUnitType> toListDTO(List<SellableUnitTypeEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    SellableUnitTypeEntity toEntity(SellableUnitType dto);

}
