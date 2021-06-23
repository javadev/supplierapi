package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.PropertyType;

import java.util.List;

public interface PropertyTypeManager {

    List<PropertyType> getPropertyTypes();

    PropertyType getPropertyTypeById(Integer id);

    PropertyType getPropertyTypeByCode(String code);
}
