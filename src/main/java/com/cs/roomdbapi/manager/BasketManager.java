package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Basket;

import java.util.List;

public interface BasketManager {

    List<Basket> getAllBasketsByPropertyId(Integer propertyId);

    boolean basketNotExistsById(Integer basketId);

    Basket getBasketById(Integer id);

    Integer getPropertyIdByBasketId(Integer basketId);

}
