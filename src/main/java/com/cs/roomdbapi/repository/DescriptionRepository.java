package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.DescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface DescriptionRepository extends JpaRepository<DescriptionEntity, Integer> {

    @Transactional(readOnly = true)
    Optional<DescriptionEntity> findByDescriptionType_Code(String descriptionTypeCode);

}
