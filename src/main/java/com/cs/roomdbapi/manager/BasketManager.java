package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Basket;
import com.cs.roomdbapi.dto.Description;
import com.cs.roomdbapi.dto.DescriptionSave;

import java.util.List;

public interface BasketManager {

    List<Basket> getAllBasketsByPropertyId(Integer propertyId);

    boolean basketNotExistsById(Integer basketId);

    Basket getBasketById(Integer id);

    Integer getPropertyIdByBasketId(Integer basketId);

    Description addBasketDescription(Integer basketId, DescriptionSave descriptionToSave);

    Integer getBasketIdByDescriptionId(Integer descriptionId);

}
