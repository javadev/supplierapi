package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.*;

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

    Description addPOIDescription(Integer poiId, DescriptionSave descriptionToSave);

    Integer getPOIIdByDescriptionId(Integer descriptionId);

}
