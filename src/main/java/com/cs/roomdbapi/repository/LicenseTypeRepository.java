package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.LicenseTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseTypeRepository extends JpaRepository<LicenseTypeEntity, Integer> {


}
