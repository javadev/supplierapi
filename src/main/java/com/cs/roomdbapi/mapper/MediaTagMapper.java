package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.MediaTag;
import com.cs.roomdbapi.model.MediaTagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MediaTagMapper {

    MediaTagMapper MAPPER = Mappers.getMapper(MediaTagMapper.class);

    MediaTag toDTO(MediaTagEntity e);

    List<MediaTag> toListDTO(List<MediaTagEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    MediaTagEntity toEntity(MediaTag dto);

}
