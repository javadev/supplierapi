package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.MediaAttributeTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MediaAttributeTypeRepository extends JpaRepository<MediaAttributeTypeEntity, Integer> {

    @Transactional(readOnly = true)
    List<MediaAttributeTypeEntity> findAllByMediaType_Id(Integer mediaTypeId);

}
