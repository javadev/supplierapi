package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.MediaAttributeType;
import com.cs.roomdbapi.model.MediaAttributeTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MediaAttributeTypeMapper {

    MediaAttributeTypeMapper MAPPER = Mappers.getMapper(MediaAttributeTypeMapper.class);

    MediaAttributeType toDTO(MediaAttributeTypeEntity e);

    List<MediaAttributeType> toListDTO(List<MediaAttributeTypeEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    MediaAttributeTypeEntity toEntity(MediaAttributeType dto);

}
