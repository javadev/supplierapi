package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.PropertyTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface PropertyTypeRepository extends JpaRepository<PropertyTypeEntity, Integer> {

    @Transactional(readOnly = true)
    Optional<PropertyTypeEntity> findByCode(String code);

}
