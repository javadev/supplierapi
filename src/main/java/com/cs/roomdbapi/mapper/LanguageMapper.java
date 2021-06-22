package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.Language;
import com.cs.roomdbapi.model.LanguageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LanguageMapper {

    LanguageMapper MAPPER = Mappers.getMapper(LanguageMapper.class);

    Language toDTO(LanguageEntity e);

    /*
     * Mapper to get codes of all types.
     */
    List<Language> toListDTO(List<LanguageEntity> list);

}
