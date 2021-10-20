package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.CalendarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CalendarRepository extends JpaRepository<CalendarEntity, Integer> {

    @Transactional(readOnly = true)
    List<CalendarEntity> findAllBySellableUnitId(Integer sellableUnitId);

    @Transactional(readOnly = true)
    List<CalendarEntity> findAllBySellableUnitIdAndCountAvailableIsNotNull(Integer sellableUnitId);

    @Transactional(readOnly = true)
    List<CalendarEntity> findAllBySellableUnitIdAndPriceIsNotNull(Integer sellableUnitId);

    @Transactional(readOnly = true)
    List<CalendarEntity> findAllBySellableUnitIdAndMinLOSIsNotNull(Integer sellableUnitId);

    @Transactional(readOnly = true)
    List<CalendarEntity> findAllBySellableUnitIdAndMaxLOSIsNotNull(Integer sellableUnitId);

    @Transactional(readOnly = true)
    List<CalendarEntity> findAllBySellableUnitIdAndClosedForSaleIsNotNull(Integer sellableUnitId);

    @Transactional(readOnly = true)
    List<CalendarEntity> findAllBySellableUnitIdAndClosedForArrivalIsNotNull(Integer sellableUnitId);

    @Transactional(readOnly = true)
    List<CalendarEntity> findAllBySellableUnitIdAndClosedForDepartureIsNotNull(Integer sellableUnitId);

    @Transactional(readOnly = true)
    Optional<CalendarEntity> findBySellableUnitIdAndDateAndTimeSegment(Integer sellableUnitId, LocalDate date, LocalTime timeSegment);

}
