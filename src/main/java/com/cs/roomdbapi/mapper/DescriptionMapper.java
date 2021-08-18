package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.Description;
import com.cs.roomdbapi.model.DescriptionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DescriptionMapper {

    DescriptionMapper MAPPER = Mappers.getMapper(DescriptionMapper.class);

    Description toDTO(DescriptionEntity e);

    List<Description> toListDTO(List<DescriptionEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    DescriptionEntity toEntity(Description dto);

}
