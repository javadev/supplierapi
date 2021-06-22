package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Property;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyManagerImpl implements PropertyManager {

    @Override
    public List<Property> getProperties() {
        return null;
    }

    @Override
    public Property getPropertyById(Long id) {

        Property property = new Property("PR1", "Property 1", "active");

        return property;
    }

    @Override
    public Property insert(Property property) {
        return null;
    }

    @Override
    public void updateProperty(Long id, Property property) {

    }

    @Override
    public void deleteProperty(Long propertyId) {

    }

}
