package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.PropertyInfo;
import com.cs.roomdbapi.dto.PropertyInfoSaveRequest;
import com.cs.roomdbapi.model.PropertyInfoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PropertyInfoMapper {

    PropertyInfoMapper MAPPER = Mappers.getMapper(PropertyInfoMapper.class);

    @Mapping(target = "languages", ignore = true)
    PropertyInfo toDTO(PropertyInfoEntity e);

    List<PropertyInfo> toListDTO(List<PropertyInfoEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "propertyType", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    PropertyInfoEntity toEntity(PropertyInfoSaveRequest dto);

}
