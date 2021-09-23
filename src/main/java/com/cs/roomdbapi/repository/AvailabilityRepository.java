package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.AvailabilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AvailabilityRepository extends JpaRepository<AvailabilityEntity, Integer> {

    @Transactional(readOnly = true)
    List<AvailabilityEntity> findAllBySellableUnitId(Integer sellableUnitId);

    @Transactional(readOnly = true)
    Optional<AvailabilityEntity> findBySellableUnitIdAndDateAndTimeSegment(Integer sellableUnitId, LocalDate date, LocalTime timeSegment);

}
