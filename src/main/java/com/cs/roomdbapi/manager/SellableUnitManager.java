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

    SellableUnit getOrCreateSellableUnitBySupplierUnitId(String supplierUnitId, Integer propertyId);

    List<SellableUnit> getAllSellableUnitsByPropertyId(Integer propertyId);

    List<SUAvailabilityResult> getAvailabilitiesBySellableUnitId(Integer sellableUnitId);

    List<SUAvailabilityResult> setAvailabilitiesToSellableUnit(Integer sellableUnitId, List<SUAvailabilitySave> availabilities);

    List<SUAvailabilityResult> setAvailabilitiesToSellableUnitForDateRange(SUAvailabilityDateRangeRequest request);

    List<SUPriceResult> getPricesBySellableUnitId(Integer sellableUnitId);

    List<SUPriceResult> setPricesToSellableUnit(Integer sellableUnitId, List<SUPriceSave> prices);

    List<SUPriceResult> setPricesToSellableUnitForDateRange(SUPriceDateRangeRequest request);

    List<SUMinLOSResult> getMinLOSRecordsBySellableUnitId(Integer sellableUnitId);

    List<SUMinLOSResult> setMinLOSRecordsToSellableUnit(Integer sellableUnitId, List<SUMinLOSSave> minLOSRecords);

    List<SUMinLOSResult> setMinLOSRecordsToSellableUnitForDateRange(SUMinLOSDateRangeRequest request);

    List<SUMaxLOSResult> getMaxLOSRecordsBySellableUnitId(Integer sellableUnitId);

    List<SUMaxLOSResult> setMaxLOSRecordsToSellableUnit(Integer sellableUnitId, List<SUMaxLOSSave> maxLOSRecords);

    List<SUMaxLOSResult> setMaxLOSRecordsToSellableUnitForDateRange(SUMaxLOSDateRangeRequest request);

    List<SUClosedForSaleResult> getClosedForSaleRecordsBySellableUnitId(Integer sellableUnitId);

    List<SUClosedForSaleResult> setClosedForSaleRecordsToSellableUnit(Integer sellableUnitId, List<SUClosedForSaleSave> closedForSaleRecords);

    List<SUClosedForSaleResult> setClosedForSaleRecordsToSellableUnitForDateRange(SUClosedForSaleDateRangeRequest request);

    List<SUClosedForArrivalResult> getClosedForArrivalRecordsBySellableUnitId(Integer sellableUnitId);

    List<SUClosedForArrivalResult> setClosedForArrivalRecordsToSellableUnit(Integer sellableUnitId, List<SUClosedForArrivalSave> closedForArrivalRecords);

    List<SUClosedForArrivalResult> setClosedForArrivalRecordsToSellableUnitForDateRange(SUClosedForArrivalDateRangeRequest request);

    List<SUClosedForDepartureResult> getClosedForDepartureRecordsBySellableUnitId(Integer sellableUnitId);

    List<SUClosedForDepartureResult> setClosedForDepartureRecordsToSellableUnit(Integer sellableUnitId, List<SUClosedForDepartureSave> closedForDepartureRecords);

    List<SUClosedForDepartureResult> setClosedForDepartureRecordsToSellableUnitForDateRange(SUClosedForDepartureDateRangeRequest request);

    Description addSellableUnitDescription(Integer sellableUnitId, DescriptionSave descriptionToSave); // TODO

    Integer getSellableUnitIdByDescriptionId(Integer descriptionId);

    List<SUCapacity> getSUCapacityBySellableUnitId(Integer sellableUnitId);

    List<SUCapacity> setSellableUnitCapacities(Integer sellableUnitId, List<SUCapacity> capacities);

    List<SUCapacity> addSellableUnitCapacities(Integer sellableUnitId, List<SUCapacity> capacities);

    List<SUCalendar> getCalendarRowsBySellableUnitId(Integer sellableUnitId);

    List<SUCalendar> setCalendarRowsToSellableUnit(Integer sellableUnitId, List<SUCalendar> calendars);

    List<SUCalendar> setCalendarRowsToSellableUnitForDateRange(SUCalendarDateRangeRequest request);

}
