package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.PricingModelTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricingModelTypeRepository extends JpaRepository<PricingModelTypeEntity, Integer> {


}
