package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.IdentifierSourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface IdentifierSourceRepository extends JpaRepository<IdentifierSourceEntity, Integer> {

    @Transactional(readOnly = true)
    Optional<IdentifierSourceEntity> findByAbbreviation(String abbreviation);

}
