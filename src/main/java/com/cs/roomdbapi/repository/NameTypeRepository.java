package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.NameTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Repository
public interface NameTypeRepository extends JpaRepository<NameTypeEntity, Integer> {

    @Transactional(readOnly = true)
    Optional<NameTypeEntity> findByCode(@NotBlank String code);

}
