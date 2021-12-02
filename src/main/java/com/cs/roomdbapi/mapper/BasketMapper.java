package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.Basket;
import com.cs.roomdbapi.model.BasketEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BasketMapper {

    BasketMapper MAPPER = Mappers.getMapper(BasketMapper.class);

    @Mapping(target = "propertyId", source = "property.id")
    Basket toDTO(BasketEntity e);

    List<Basket> toListDTO(List<BasketEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "property", ignore = true)
    @Mapping(target = "descriptions", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    BasketEntity saveRequestToEntity(Basket dto);

}
