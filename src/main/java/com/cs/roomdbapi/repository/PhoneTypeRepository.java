package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.PhoneTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneTypeRepository extends JpaRepository<PhoneTypeEntity, Integer> {


}
