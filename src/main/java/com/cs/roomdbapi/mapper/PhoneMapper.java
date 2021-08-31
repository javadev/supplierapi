package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.Phone;
import com.cs.roomdbapi.model.PhoneEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PhoneMapper {

    PhoneMapper MAPPER = Mappers.getMapper(PhoneMapper.class);

    Phone toDTO(PhoneEntity e);

    List<Phone> toListDTO(List<PhoneEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    PhoneEntity toEntity(Phone dto);

}
