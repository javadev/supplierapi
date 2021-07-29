package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.PredefinedTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredefinedTagRepository extends JpaRepository<PredefinedTagEntity, Integer> {

}
