package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.SUCapacityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SUCapacityRepository extends JpaRepository<SUCapacityEntity, Integer> {

    @Transactional(readOnly = true)
    List<SUCapacityEntity> findAllBySellableUnitId(Integer sellableUnitId);

    @Transactional
    Integer removeBySellableUnitId(Integer sellableUnitId);

}
