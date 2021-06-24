package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.State;
import com.cs.roomdbapi.model.StateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface StateMapper {

    StateMapper MAPPER = Mappers.getMapper(StateMapper.class);

    @Mapping(target = "countryCode", source = "country.code")
    @Mapping(target = "languageCode", source = "language.code")
    State toDTO(StateEntity e);

    List<State> toListDTO(List<StateEntity> list);

}
