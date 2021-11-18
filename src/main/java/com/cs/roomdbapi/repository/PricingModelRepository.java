package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.PricingModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PricingModelRepository extends JpaRepository<PricingModelEntity, Integer> {

    @Transactional(readOnly = true)
    Boolean existsByProperty_IdAndName(Integer propertyId, String name);

}
