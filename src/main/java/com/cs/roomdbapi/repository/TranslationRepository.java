package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.CountryTranslationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TranslationRepository extends JpaRepository<CountryTranslationEntity, Integer> {

    @Transactional(readOnly = true)
    List<CountryTranslationEntity> findAllByLanguage_Code(String langCode);

    @Transactional(readOnly = true)
    CountryTranslationEntity findByLanguage_CodeAndCountryId(String langCode, Integer id);

}
