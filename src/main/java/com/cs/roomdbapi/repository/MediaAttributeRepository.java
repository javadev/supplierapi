package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.MediaAttributeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MediaAttributeRepository extends JpaRepository<MediaAttributeEntity, Integer> {

    @Transactional(readOnly = true)
    List<MediaAttributeEntity> findAllByMediaId(Integer mediaId);

    @Transactional(readOnly = true)
    Optional<MediaAttributeEntity> findTopByMediaIdAndAndMediaAttributeType_Id(Integer mediaId, Integer mediaAttributeTypeId);

    @Transactional(readOnly = false)
    void deleteByMediaId(Integer mediaId);
}
