package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Supplier;

import javax.servlet.http.HttpServletRequest;

public interface ValidationManager {

    void validateSellableUnitAccess(Integer sellableUnitId, HttpServletRequest req);

    void validatePropertyAccess(HttpServletRequest req, Supplier supplier, Integer propertyId);

    boolean isHasAllPropertiesPermission();

}
