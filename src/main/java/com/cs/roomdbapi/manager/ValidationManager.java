package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Property;
import com.cs.roomdbapi.dto.Supplier;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ValidationManager {

    void validateSellableUnitAccess(Integer sellableUnitId, HttpServletRequest req);

    void validatePricingModelAccess(Integer sellableUnitId, HttpServletRequest req);

    void validatePropertyAccess(HttpServletRequest req, Supplier supplier, Integer propertyId);

    void validatePropertiesList(HttpServletRequest req, List<Property> properties);

    boolean hasAccessToProperty(HttpServletRequest req, Supplier supplier, Integer propertyId);

    boolean isHasAllPropertiesPermission();

}
