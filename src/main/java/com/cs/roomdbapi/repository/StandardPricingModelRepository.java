package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.StandardPricingModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandardPricingModelRepository extends JpaRepository<StandardPricingModelEntity, Integer> {

}
