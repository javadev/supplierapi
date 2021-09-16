package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.Address;
import com.cs.roomdbapi.dto.AddressSave;
import com.cs.roomdbapi.model.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AddressMapper {

    AddressMapper MAPPER = Mappers.getMapper(AddressMapper.class);

    Address toDTO(AddressEntity e);

    List<Address> toListDTO(List<AddressEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "language", ignore = true)
    @Mapping(target = "country", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    AddressEntity toEntity(AddressSave dto);

}
