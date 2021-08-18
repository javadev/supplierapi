package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.Media;
import com.cs.roomdbapi.dto.MediaSaveRequest;
import com.cs.roomdbapi.model.MediaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MediaMapper {

    MediaMapper MAPPER = Mappers.getMapper(MediaMapper.class);

    @Mapping(target = "propertyId", source = "property.id")
    Media toDTO(MediaEntity e);

    List<Media> toListDTO(List<MediaEntity> list);

    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "property", ignore = true)
    @Mapping(target = "mediaType", ignore = true)
    @Mapping(target = "licenseType", ignore = true)
    @Mapping(target = "url", ignore = true)
    @Mapping(target = "descriptions", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    MediaEntity saveRequestToEntity(MediaSaveRequest dto);

}
