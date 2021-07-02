package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.Language;
import com.cs.roomdbapi.model.LanguageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LanguageMapper {

    LanguageMapper MAPPER = Mappers.getMapper(LanguageMapper.class);

    Language toDTO(LanguageEntity e);

    List<Language> toListDTO(List<LanguageEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    LanguageEntity toEntity(Language dto);

}
