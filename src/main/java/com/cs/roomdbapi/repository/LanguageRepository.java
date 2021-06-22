package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface LanguageRepository extends JpaRepository<LanguageEntity, Integer> {

    @Transactional(readOnly = true)
    Optional<LanguageEntity> findByCode(String code);

}
