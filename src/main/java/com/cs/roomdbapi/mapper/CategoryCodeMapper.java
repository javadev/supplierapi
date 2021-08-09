package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.CategoryCode;
import com.cs.roomdbapi.model.CategoryCodeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CategoryCodeMapper {

    CategoryCodeMapper MAPPER = Mappers.getMapper(CategoryCodeMapper.class);

    CategoryCode toDTO(CategoryCodeEntity e);

    List<CategoryCode> toListDTO(List<CategoryCodeEntity> list);

}
