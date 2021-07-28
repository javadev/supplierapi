package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.MediaTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaTypeRepository extends JpaRepository<MediaTypeEntity, Integer> {


}
