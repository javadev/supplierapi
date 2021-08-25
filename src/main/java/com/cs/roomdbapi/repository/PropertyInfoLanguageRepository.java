package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.PropertyInfoLanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Repository
public interface PropertyInfoLanguageRepository extends JpaRepository<PropertyInfoLanguageEntity, Integer> {

    @Transactional(readOnly = true)
    List<PropertyInfoLanguageEntity> findAllByPropertyInfoId(@NotBlank Integer propertyInfoId);

    @Transactional()
    void deleteByPropertyInfoId(Integer propertyInfoId);

}
