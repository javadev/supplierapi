package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Integer> {

    @Transactional(readOnly = true)
    Optional<CountryEntity> findByCode(String code);

}
