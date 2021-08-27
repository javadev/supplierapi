package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.EmailType;
import com.cs.roomdbapi.model.EmailTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EmailTypeMapper {

    EmailTypeMapper MAPPER = Mappers.getMapper(EmailTypeMapper.class);

    EmailType toDTO(EmailTypeEntity e);

    List<EmailType> toListDTO(List<EmailTypeEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    EmailTypeEntity toEntity(EmailType dto);

}
