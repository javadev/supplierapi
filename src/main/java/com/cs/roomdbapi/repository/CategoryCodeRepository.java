package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.CategoryCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryCodeRepository extends JpaRepository<CategoryCodeEntity, Integer> {

}
