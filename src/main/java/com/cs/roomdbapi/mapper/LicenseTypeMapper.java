package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.LicenseType;
import com.cs.roomdbapi.model.LicenseTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LicenseTypeMapper {

    LicenseTypeMapper MAPPER = Mappers.getMapper(LicenseTypeMapper.class);

    LicenseType toDTO(LicenseTypeEntity e);

    List<LicenseType> toListDTO(List<LicenseTypeEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    LicenseTypeEntity toEntity(LicenseType dto);

}
