package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.PropertyIdentifier;
import com.cs.roomdbapi.model.PropertyIdentifierEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PropertyIdentifierMapper {

    PropertyIdentifierMapper MAPPER = Mappers.getMapper(PropertyIdentifierMapper.class);

    PropertyIdentifier toDTO(PropertyIdentifierEntity e);

    List<PropertyIdentifier> toListDTO(List<PropertyIdentifierEntity> list);

}
