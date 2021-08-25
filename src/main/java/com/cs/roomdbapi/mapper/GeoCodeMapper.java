package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.GeoCode;
import com.cs.roomdbapi.model.GeoCodeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GeoCodeMapper {

    GeoCodeMapper MAPPER = Mappers.getMapper(GeoCodeMapper.class);

    GeoCode toDTO(GeoCodeEntity e);

    List<GeoCode> toListDTO(List<GeoCodeEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    GeoCodeEntity toEntity(GeoCode dto);

}
