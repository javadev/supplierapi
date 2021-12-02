package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.Name;
import com.cs.roomdbapi.model.NameEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface NameMapper {

    NameMapper MAPPER = Mappers.getMapper(NameMapper.class);

    Name toDTO(NameEntity e);

    List<Name> toListDTO(List<NameEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    NameEntity toEntity(Name dto);

}
