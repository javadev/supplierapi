package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.Supplier;
import com.cs.roomdbapi.model.SupplierEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SupplierMapper {

    SupplierMapper MAPPER = Mappers.getMapper(SupplierMapper.class);

    Supplier toDTO(SupplierEntity e);

    List<Supplier> toListDTO(List<SupplierEntity> list);

}
