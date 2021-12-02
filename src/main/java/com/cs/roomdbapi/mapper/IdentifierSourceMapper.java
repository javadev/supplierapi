package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.IdentifierSource;
import com.cs.roomdbapi.model.IdentifierSourceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IdentifierSourceMapper {

    IdentifierSourceMapper MAPPER = Mappers.getMapper(IdentifierSourceMapper.class);

    IdentifierSource toDTO(IdentifierSourceEntity e);

    List<IdentifierSource> toListDTO(List<IdentifierSourceEntity> list);

}
