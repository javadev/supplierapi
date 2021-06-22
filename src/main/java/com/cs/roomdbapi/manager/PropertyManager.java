package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Property;

import java.util.List;

public interface PropertyManager {

    List<Property> getProperties();

    Property getPropertyById(Long id);

    Property insert(Property property);

    void updateProperty(Long id, Property property);

    void deleteProperty(Long propertyId);
}
