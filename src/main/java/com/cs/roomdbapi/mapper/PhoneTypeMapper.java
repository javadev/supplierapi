package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.PhoneType;
import com.cs.roomdbapi.model.PhoneTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PhoneTypeMapper {

    PhoneTypeMapper MAPPER = Mappers.getMapper(PhoneTypeMapper.class);

    PhoneType toDTO(PhoneTypeEntity e);

    List<PhoneType> toListDTO(List<PhoneTypeEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    PhoneTypeEntity toEntity(PhoneType dto);

}
