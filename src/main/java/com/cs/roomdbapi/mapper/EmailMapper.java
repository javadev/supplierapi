package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.Email;
import com.cs.roomdbapi.model.EmailEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EmailMapper {

    EmailMapper MAPPER = Mappers.getMapper(EmailMapper.class);

    Email toDTO(EmailEntity e);

    List<Email> toListDTO(List<EmailEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    EmailEntity toEntity(Email dto);

}
