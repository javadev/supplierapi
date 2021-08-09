package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.CategoryCode;
import com.cs.roomdbapi.dto.PointOfInterest;
import com.cs.roomdbapi.dto.PointOfInterestSaveRequest;

import java.util.List;

public interface PointOfInterestManager {

    List<PointOfInterest> getAllPOI();

    PointOfInterest getPOIById(Integer id);

    boolean poiNotExistsById(Integer poiId);

    List<PointOfInterest> getAllPointOfInterestByPropertyId(Integer propertyId);

    Integer getPropertyIdByPOIId(Integer poiId);

    void deletePOI(Integer poiId);

    PointOfInterest addPOI(PointOfInterestSaveRequest poi);

    List<CategoryCode> getAllCategoryCodes();

    PointOfInterest updatePOI(Integer id, PointOfInterestSaveRequest poi);

}
