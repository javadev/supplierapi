package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Integer> {

    @Transactional(readOnly = true)
    Optional<CurrencyEntity> findByCode(@NotBlank String code);

    @Transactional(readOnly = true)
    Optional<CurrencyEntity> findByNumericCode(@NotBlank Integer code);

    @Transactional(readOnly = true)
    Boolean existsByCode(@NotBlank String code);

    @Transactional(readOnly = true)
    Boolean existsByNumericCode(@NotBlank Integer code);

}
