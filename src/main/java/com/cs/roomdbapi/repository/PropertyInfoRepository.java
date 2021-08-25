package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.PropertyInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Repository
public interface PropertyInfoRepository extends JpaRepository<PropertyInfoEntity, Integer> {

    @Transactional(readOnly = true)
    Optional<PropertyInfoEntity> findByPropertyId(@NotBlank Integer propertyId);

}
