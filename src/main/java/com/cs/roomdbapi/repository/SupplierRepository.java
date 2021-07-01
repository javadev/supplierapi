package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    boolean existsByName(String name);

    Supplier findByName(String name);

    @Transactional
    void deleteByName(String name);

}
