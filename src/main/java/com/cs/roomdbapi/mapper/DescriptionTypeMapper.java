package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.DescriptionType;
import com.cs.roomdbapi.model.DescriptionTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DescriptionTypeMapper {

    DescriptionTypeMapper MAPPER = Mappers.getMapper(DescriptionTypeMapper.class);

    DescriptionType toDTO(DescriptionTypeEntity e);

    List<DescriptionType> toListDTO(List<DescriptionTypeEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    DescriptionTypeEntity toEntity(DescriptionType dto);

}
