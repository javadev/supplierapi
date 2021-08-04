package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.MediaTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MediaTypeRepository extends JpaRepository<MediaTypeEntity, Integer> {

    @Transactional(readOnly = true)
    @Query("select mt.id from MediaTypeEntity mt where mt.code = ?1")
    Integer getMediaTypeIdByCode(String code);

}
