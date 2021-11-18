package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.OccupancyBasedPricingModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OccupancyBasedPricingModelRepository extends JpaRepository<OccupancyBasedPricingModelEntity, Integer> {

}
