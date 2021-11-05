package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.BasketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BasketRepository extends JpaRepository<BasketEntity, Integer> {

    @Transactional(readOnly = true)
    List<BasketEntity> findAllByProperty_Id(Integer propertyId);

    @Transactional(readOnly = true)
    @Query("select b.property.id from BasketEntity b where b.id = ?1")
    Integer getPropertyIdByBasketId(Integer basketId);

    @Transactional(readOnly = true)
    @Query(value = "select bxd.basket_id from basket_x_description bxd where bxd.description_id = ?1", nativeQuery = true)
    Integer getBasketIdByDescriptionId(Integer descriptionId);

}
