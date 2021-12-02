package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.BasketSellableUnit;
import com.cs.roomdbapi.model.BasketSellableUnitEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BasketSellableUnitMapper {

    BasketSellableUnitMapper MAPPER = Mappers.getMapper(BasketSellableUnitMapper.class);

    BasketSellableUnit toDTO(BasketSellableUnitEntity e);

    List<BasketSellableUnit> toListDTO(List<BasketSellableUnitEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    BasketSellableUnitEntity toEntity(BasketSellableUnit dto);

}
