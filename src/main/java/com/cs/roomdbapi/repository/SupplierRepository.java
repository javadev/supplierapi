package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends JpaRepository<SupplierEntity, Integer> {

    Optional<SupplierEntity> findByName(String name);

    List<SupplierEntity> findByIsActiveIsTrueAndWebhookIsNotNull();

}
