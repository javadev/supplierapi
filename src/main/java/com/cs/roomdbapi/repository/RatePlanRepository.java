package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.RatePlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RatePlanRepository extends JpaRepository<RatePlanEntity, Integer> {

    @Transactional(readOnly = true)
    List<RatePlanEntity> findAllByProperty_Id(Integer propertyId);

    @Transactional(readOnly = true)
    @Query("select rp.property.id from RatePlanEntity rp where rp.id = ?1")
    Integer getPropertyIdByRatePlanId(Integer ratePlanId);

    @Transactional(readOnly = true)
    Boolean existsByProperty_IdAndName(Integer propertyId, String name);

    @Transactional(readOnly = true)
    List<RatePlanEntity> findAllByParent_Id(Integer parentId);

}
