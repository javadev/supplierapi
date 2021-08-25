package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.Brand;
import com.cs.roomdbapi.model.BrandEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BrandMapper {

    BrandMapper MAPPER = Mappers.getMapper(BrandMapper.class);

    Brand toDTO(BrandEntity e);

    List<Brand> toListDTO(List<BrandEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    BrandEntity toEntity(Brand dto);

}
