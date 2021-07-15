package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Property;
import com.cs.roomdbapi.dto.PropertySaveRequest;

import java.util.List;

public interface PropertyManager {

    List<Property> getProperties();

    List<Property> getPropertiesBySupplier(String supplierName);

    Property getPropertyById(Integer id);

    Property getPropertyBySupplierPropertyId(String id);

    Property addProperty(PropertySaveRequest property, String supplierName);

    Property updateProperty(Integer id, PropertySaveRequest property, String supplierName);

}
