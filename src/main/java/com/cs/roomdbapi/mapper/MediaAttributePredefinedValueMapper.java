package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.MediaAttributePredefinedValue;
import com.cs.roomdbapi.model.MediaAttributePredefinedValueEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MediaAttributePredefinedValueMapper {

    MediaAttributePredefinedValueMapper MAPPER = Mappers.getMapper(MediaAttributePredefinedValueMapper.class);

    MediaAttributePredefinedValue toDTO(MediaAttributePredefinedValueEntity e);

    List<MediaAttributePredefinedValue> toListDTO(List<MediaAttributePredefinedValueEntity> list);

    @Mapping(target = "mediaAttributeTypeId", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    MediaAttributePredefinedValueEntity toEntity(MediaAttributePredefinedValue dto);

}
