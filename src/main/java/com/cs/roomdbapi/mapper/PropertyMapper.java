package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.Property;
import com.cs.roomdbapi.dto.PropertySaveRequest;
import com.cs.roomdbapi.model.PropertyEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PropertyMapper {

    PropertyMapper MAPPER = Mappers.getMapper(PropertyMapper.class);

    Property toDTO(PropertyEntity e);

    List<Property> toListDTO(List<PropertyEntity> list);

    @IterableMapping(qualifiedByName="mapWithoutSupplier")
    List<Property> toListDTOWithoutSupplier(List<PropertyEntity> list);

    @Named("mapWithoutSupplier")
    @Mapping(target = "supplier", ignore = true)
    Property mapWithoutSupplier(PropertyEntity source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    PropertyEntity toEntity(Property dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    @Mapping(target = "homeCurrency", ignore = true)
    @Mapping(target = "descriptions", ignore = true)
    @Mapping(target = "supplier", ignore = true)
    PropertyEntity saveRequestToEntity(PropertySaveRequest dto);

}
