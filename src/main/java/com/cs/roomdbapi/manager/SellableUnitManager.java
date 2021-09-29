package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.*;

import java.util.List;

public interface SellableUnitManager {

    List<SellableUnitType> getAllSellableUnitTypes();

    SellableUnit addSellableUnit(SellableUnitSaveRequest request);

    boolean sellableUnitNotExistsById(Integer sellableUnitId);

    Integer getPropertyIdBySellableUnitId(Integer sellableUnitId);

    void deleteSellableUnit(Integer id);

    SellableUnit getSellableUnitById(Integer id);

    SellableUnit getSellableUnitBySupplierUnitId(String id);

    SellableUnit getOrCreateSellableUnitBySupplierUnitId(String sellableUnitId, Integer propertyId);

    List<SellableUnit> getAllSellableUnitsByPropertyId(Integer propertyId);

    List<Availability> getAvailabilitiesBySellableUnitId(Integer sellableUnitId);

    List<Availability> setSellableUnitAvailabilities(Integer sellableUnitId, List<AvailabilitySave> availabilities);

    Description addSellableUnitDescription(Integer sellableUnitId, DescriptionSave descriptionToSave); // TODO

    Integer getSellableUnitIdByDescriptionId(Integer descriptionId);

    List<SUCapacity> getSUCapacityBySellableUnitId(Integer sellableUnitId);

    List<SUCapacity> setSellableUnitCapacities(Integer sellableUnitId, List<SUCapacity> capacities);

    List<SUCapacity> addSellableUnitCapacities(Integer sellableUnitId, List<SUCapacity> capacities);

}
