package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.PropertyInfoLanguage;
import com.cs.roomdbapi.model.PropertyInfoLanguageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PropertyInfoLanguageMapper {

    PropertyInfoLanguageMapper MAPPER = Mappers.getMapper(PropertyInfoLanguageMapper.class);

    PropertyInfoLanguage toDTO(PropertyInfoLanguageEntity e);

    List<PropertyInfoLanguage> toListDTO(List<PropertyInfoLanguageEntity> list);

    @Mapping(target = "propertyInfoId", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    PropertyInfoLanguageEntity toEntity(PropertyInfoLanguage dto);

}
