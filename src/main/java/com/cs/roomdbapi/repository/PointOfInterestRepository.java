package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.PointOfInterestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PointOfInterestRepository extends JpaRepository<PointOfInterestEntity, Integer> {

    @Transactional(readOnly = true)
    @Query("select poi.property.id from PointOfInterestEntity poi where poi.id = ?1")
    Integer getPropertyIdByPOIId(Integer poiId);

    @Transactional(readOnly = true)
    List<PointOfInterestEntity> findAllByProperty_Id(Integer propertyId);

    @Transactional(readOnly = true)
    @Query(value = "select poixd.point_of_interest_id from point_of_interest_x_description poixd where poixd.description_id = ?1", nativeQuery = true)
    Integer getPOIIdByDescriptionId(Integer descriptionId);

}
