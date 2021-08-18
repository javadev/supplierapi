package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.DescriptionTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Repository
public interface DescriptionTypeRepository extends JpaRepository<DescriptionTypeEntity, Integer> {

    @Transactional(readOnly = true)
    Optional<DescriptionTypeEntity> findByCode(@NotBlank String code);

}
