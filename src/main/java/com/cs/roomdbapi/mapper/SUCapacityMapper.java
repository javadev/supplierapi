package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.SUCapacity;
import com.cs.roomdbapi.model.SUCapacityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SUCapacityMapper {

    SUCapacityMapper MAPPER = Mappers.getMapper(SUCapacityMapper.class);

    SUCapacity toDTO(SUCapacityEntity e);

    List<SUCapacity> toListDTO(List<SUCapacityEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    SUCapacityEntity toEntity(SUCapacity dto);

}
