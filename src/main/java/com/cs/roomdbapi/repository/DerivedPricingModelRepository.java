package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.DerivedPricingModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DerivedPricingModelRepository extends JpaRepository<DerivedPricingModelEntity, Integer> {

}
