package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.PhoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<PhoneEntity, Integer> {


}
