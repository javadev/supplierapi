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

    @Named("common")
    Property toDTO(PropertyEntity e);

    @Mapping(target = "code", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "alternativeName", ignore = true)
    @Mapping(target = "cultSwitchId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "forTesting", ignore = true)
    @Mapping(target = "homeCurrency", ignore = true)
    @Mapping(target = "phones", ignore = true)
    @Mapping(target = "emails", ignore = true)
    @Mapping(target = "descriptions", ignore = true)
    Property toDTOIsMaster(PropertyEntity e);

    @IterableMapping(qualifiedByName="common")
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
