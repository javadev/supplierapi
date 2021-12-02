package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.SellableUnitTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellableUnitTypeRepository extends JpaRepository<SellableUnitTypeEntity, Integer> {


}
