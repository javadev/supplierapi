package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.MediaType;
import com.cs.roomdbapi.model.MediaTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MediaTypeMapper {

    MediaTypeMapper MAPPER = Mappers.getMapper(MediaTypeMapper.class);

    MediaType toDTO(MediaTypeEntity e);

    List<MediaType> toListDTO(List<MediaTypeEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    MediaTypeEntity toEntity(MediaType dto);

}
