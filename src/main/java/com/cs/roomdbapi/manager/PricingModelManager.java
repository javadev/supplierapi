package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.*;

import java.util.List;

public interface PricingModelManager {

    List<PricingModelType> getPricingModelTypes();

    PricingModelType getPricingModelTypeById(Integer id);

    PricingModel addPricingModel(PricingModelSaveRequest request);

    List<PricingModel> getAllPricingModelsByPropertyId(Integer propertyId);

    PricingModel getPricingModelById(Integer id);

    boolean pricingModelNotExistsById(Integer pricingModelId);

    Integer getPropertyIdByPricingModelId(Integer pricingModelId);

    void deletePricingModel(Integer id);

}
