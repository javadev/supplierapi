package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.Product;
import com.cs.roomdbapi.model.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {

    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "propertyId", source = "property.id")
    Product toDTO(ProductEntity e);

    List<Product> toListDTO(List<ProductEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "property", ignore = true)
    @Mapping(target = "descriptions", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    ProductEntity saveRequestToEntity(Product dto);

}
