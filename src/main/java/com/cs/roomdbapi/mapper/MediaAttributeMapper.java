package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.MediaAttribute;
import com.cs.roomdbapi.model.MediaAttributeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MediaAttributeMapper {

    MediaAttributeMapper MAPPER = Mappers.getMapper(MediaAttributeMapper.class);

    MediaAttribute toDTO(MediaAttributeEntity e);

    List<MediaAttribute> toListDTO(List<MediaAttributeEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    MediaAttributeEntity toEntity(MediaAttribute dto);

}
