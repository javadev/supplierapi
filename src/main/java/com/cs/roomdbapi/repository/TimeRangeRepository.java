package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.TimeRangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeRangeRepository extends JpaRepository<TimeRangeEntity, Integer> {

}
