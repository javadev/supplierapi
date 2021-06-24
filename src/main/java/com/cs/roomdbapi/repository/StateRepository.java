package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<StateEntity, Integer> {

    @Transactional(readOnly = true)
    Optional<StateEntity> findByCode(String code);

    @Transactional(readOnly = true)
    List<StateEntity> findAllByCountry_Code(String countryCode);

}
