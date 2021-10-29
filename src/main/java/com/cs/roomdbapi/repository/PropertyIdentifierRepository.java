package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.IdentifierSourceEntity;
import com.cs.roomdbapi.model.PropertyIdentifierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyIdentifierRepository extends JpaRepository<PropertyIdentifierEntity, Integer> {

    @Transactional(readOnly = true)
    List<PropertyIdentifierEntity> findAllByPropertyId(@NotBlank Integer propertyId);

    @Transactional(readOnly = true)
    Optional<PropertyIdentifierEntity> findByPropertyIdAndSource(@NotBlank Integer propertyId, @NotNull IdentifierSourceEntity source);

}
