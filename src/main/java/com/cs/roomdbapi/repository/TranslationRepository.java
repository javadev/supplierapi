package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.CountryTranslationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslationRepository extends JpaRepository<CountryTranslationEntity, Integer> {
}
