package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.LengthOfStayPricingModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LengthOfStayPricingModelRepository extends JpaRepository<LengthOfStayPricingModelEntity, Integer> {

}
