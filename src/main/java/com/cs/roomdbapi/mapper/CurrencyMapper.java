package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.Currency;
import com.cs.roomdbapi.model.CurrencyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CurrencyMapper {

    CurrencyMapper MAPPER = Mappers.getMapper(CurrencyMapper.class);

    Currency toDTO(CurrencyEntity e);

    List<Currency> toListDTO(List<CurrencyEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    CurrencyEntity toEntity(Currency dto);

}
