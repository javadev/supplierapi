package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.BasketSellableUnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BasketSellableUnitRepository extends JpaRepository<BasketSellableUnitEntity, Integer> {

    @Transactional(readOnly = true)
    List<BasketSellableUnitEntity> findAllByBasketId(Integer basketId);

    @Transactional()
    void deleteByBasketId(Integer basketId);
}
