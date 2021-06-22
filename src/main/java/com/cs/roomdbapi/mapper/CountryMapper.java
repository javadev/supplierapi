package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.Country;
import com.cs.roomdbapi.model.CountryEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CountryMapper {

    CountryMapper MAPPER = Mappers.getMapper(CountryMapper.class);

    @Mapping(target = "name", ignore = true)
    @Mapping(target = "fullName", ignore = true)
    Country toDTO(CountryEntity e);

    /*
    * Mapper to get only main codes.
    */
    @Mappings({
            @Mapping(target = "codeA3", ignore = true),
            @Mapping(target = "codeNumeric", ignore = true),
            @Mapping(target = "name", ignore = true),
            @Mapping(target = "fullName", ignore = true)
    })
    Country toDTOWithCode(CountryEntity e);

    /*
    * Mapper to get only A3 codes.
    */
    @Mappings({
            @Mapping(target = "code", ignore = true),
            @Mapping(target = "codeNumeric", ignore = true),
            @Mapping(target = "name", ignore = true),
            @Mapping(target = "fullName", ignore = true)
    })
    Country toDTOWithCodeA3(CountryEntity e);

    /*
    * Mapper to get only Numeric codes.
    */
    @Mappings({
            @Mapping(target = "code", ignore = true),
            @Mapping(target = "codeA3", ignore = true),
            @Mapping(target = "name", ignore = true),
            @Mapping(target = "fullName", ignore = true)
    })
    Country toDTOWithNumeric(CountryEntity e);

}
