package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.PricingModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PricingModelRepository extends JpaRepository<PricingModelEntity, Integer> {

    @Transactional(readOnly = true)
    Boolean existsByProperty_IdAndName(Integer propertyId, String name);

    @Transactional(readOnly = true)
    List<PricingModelEntity> findAllByProperty_Id(Integer propertyId);

    @Transactional(readOnly = true)
    @Query("select pm.property.id from PricingModelEntity pm where pm.id = ?1")
    Integer getPropertyIdByPricingModelId(Integer sellableUnitId);
    
}
