package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.MediaTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MediaTagRepository extends JpaRepository<MediaTagEntity, Integer> {

    @Transactional(readOnly = true)
    List<MediaTagEntity> findAllByText(String text);

    @Transactional(readOnly = false)
    void deleteByMediaId(Integer mediaId);

}
