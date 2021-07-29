package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.PredefinedTag;
import com.cs.roomdbapi.model.PredefinedTagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PredefinedTagMapper {

    PredefinedTagMapper MAPPER = Mappers.getMapper(PredefinedTagMapper.class);

    @Mapping(target = "children", ignore = true)
    PredefinedTag toDTO(PredefinedTagEntity e);

    List<PredefinedTag> toListDTO(List<PredefinedTagEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    PredefinedTagEntity toEntity(PredefinedTag dto);

}
