package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.PropertyIdentifier;
import com.cs.roomdbapi.model.PropertyIdentifierEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PropertyIdentifierMapper {

    PropertyIdentifierMapper MAPPER = Mappers.getMapper(PropertyIdentifierMapper.class);

    @Mapping(target = "propertyId", ignore = true)
    PropertyIdentifier toDTO(PropertyIdentifierEntity e);

    @Mapping(target = "propertyId", ignore = true)
    List<PropertyIdentifier> toListDTO(List<PropertyIdentifierEntity> list);

}
