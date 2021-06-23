package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.PropertyType;
import com.cs.roomdbapi.model.PropertyTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PropertyTypeMapper {

    PropertyTypeMapper MAPPER = Mappers.getMapper(PropertyTypeMapper.class);

    PropertyType toDTO(PropertyTypeEntity e);

    List<PropertyType> toListDTO(List<PropertyTypeEntity> list);

}
