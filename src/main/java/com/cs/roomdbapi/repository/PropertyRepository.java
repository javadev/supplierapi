package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.PropertyEntity;
import com.cs.roomdbapi.model.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<PropertyEntity, Integer> {

    @Transactional(readOnly = true)
    Optional<PropertyEntity> findBySupplierPropertyId(@NotBlank String id);

    @Transactional(readOnly = true)
    Boolean existsBySupplierPropertyId(@NotBlank String code);

    @Transactional(readOnly = true)
    List<PropertyEntity> findAllBySupplierIs(SupplierEntity s);

    @Transactional(readOnly = true)
    @Query(value = "select pxd.property_id from property_x_description pxd where pxd.description_id = ?1", nativeQuery = true)
    Integer getPropertyIdByDescriptionId(Integer descriptionId);

}
