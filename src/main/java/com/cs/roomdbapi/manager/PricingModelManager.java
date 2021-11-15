package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.PricingModelType;

import java.util.List;

public interface PricingModelManager {

    List<PricingModelType> getPricingModelTypes();

    PricingModelType getPricingModelTypeById(Integer id);

}
