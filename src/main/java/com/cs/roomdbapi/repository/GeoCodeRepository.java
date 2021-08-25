package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.GeoCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeoCodeRepository extends JpaRepository<GeoCodeEntity, Integer> {

}
