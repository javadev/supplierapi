package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    @Transactional(readOnly = true)
    List<ProductEntity> findAllByProperty_Id(Integer propertyId);

    @Transactional(readOnly = true)
    @Query("select p.property.id from ProductEntity p where p.id = ?1")
    Integer getPropertyIdByProductId(Integer productId);

}
