package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.SellableUnit;
import com.cs.roomdbapi.dto.SellableUnitSaveRequest;
import com.cs.roomdbapi.model.SellableUnitEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SellableUnitMapper {

    SellableUnitMapper MAPPER = Mappers.getMapper(SellableUnitMapper.class);

    @Mapping(target = "propertyId", source = "property.id")
    SellableUnit toDTO(SellableUnitEntity e);

    List<SellableUnit> toListDTO(List<SellableUnitEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "names", ignore = true)
    @Mapping(target = "property", ignore = true)
    @Mapping(target = "sellableUnitType", ignore = true)
    @Mapping(target = "descriptions", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    SellableUnitEntity toEntity(SellableUnitSaveRequest dto);

}
