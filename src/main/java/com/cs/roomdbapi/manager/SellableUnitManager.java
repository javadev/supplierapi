package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.SellableUnit;
import com.cs.roomdbapi.dto.SellableUnitSaveRequest;
import com.cs.roomdbapi.dto.SellableUnitType;

import java.util.List;

public interface SellableUnitManager {

    List<SellableUnitType> getAllSellableUnitTypes();

    SellableUnit addSellableUnit(SellableUnitSaveRequest request);

    boolean sellableUnitNotExistsById(Integer sellableUnitId);

    Integer getPropertyIdBySellableUnitId(Integer sellableUnitId);

    void deleteSellableUnit(Integer id);

    SellableUnit getSellableUnitById(Integer id);

    List<SellableUnit> getAllSellableUnitsByPropertyId(Integer propertyId);
}
