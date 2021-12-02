package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.NameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NameRepository extends JpaRepository<NameEntity, Integer> {

}
