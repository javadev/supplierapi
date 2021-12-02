package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.*;

import java.util.List;

public interface BasketManager {

    List<Basket> getAllBasketsByPropertyId(Integer propertyId);

    boolean basketNotExistsById(Integer basketId);

    Basket getBasketById(Integer id);

    Basket addBasket(Basket basket);

    Basket updateBasket(Integer id, BasketUpdate basket);

    void deleteBasket(Integer id);

    Integer getPropertyIdByBasketId(Integer basketId);

    Description addBasketDescription(Integer basketId, DescriptionSave descriptionToSave);

    Integer getBasketIdByDescriptionId(Integer descriptionId);

    List<BasketSellableUnit> getSellableUnitsByBasketId(Integer basketId);

    List<BasketSellableUnit> setSellableUnits(BasketSellableUnitRequest basketSellableUnits);

    BasketSellableUnit addSellableUnit(BasketSellableUnitSaveOne req);

    List<Basket> getBasketsBySellableUnitId(Integer sellableUnitId);

    void deleteSellableUnitFromBasket(Integer basketId, Integer sellableUnitId);

}
