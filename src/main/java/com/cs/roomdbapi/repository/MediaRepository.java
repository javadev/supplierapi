package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.MediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<MediaEntity, Integer> {

    @Transactional(readOnly = true)
    List<MediaEntity> findAllByProperty_Id(Integer propertyId);

    @Transactional(readOnly = true)
    List<MediaEntity> findAllByProperty_IdAndIsLogo(Integer propertyId, boolean isLogo);

    @Transactional(readOnly = true)
    @Query("select m.property.id from MediaEntity m where m.id = ?1")
    Integer getPropertyIdByMediaId(Integer mediaId);

}
