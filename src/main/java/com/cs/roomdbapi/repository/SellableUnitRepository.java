package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.PropertyEntity;
import com.cs.roomdbapi.model.SellableUnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@Repository
public interface SellableUnitRepository extends JpaRepository<SellableUnitEntity, Integer> {

    @Transactional(readOnly = true)
    @Query("select su.property.id from SellableUnitEntity su where su.id = ?1")
    Integer getPropertyIdBySellableUnitId(Integer sellableUnitId);

    @Transactional(readOnly = true)
    List<SellableUnitEntity> findAllByProperty_Id(Integer propertyId);

    @Transactional(readOnly = true)
    Optional<SellableUnitEntity> findBySupplierUnitId(@NotBlank String id);

}
