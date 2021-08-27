package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.EmailTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailTypeRepository extends JpaRepository<EmailTypeEntity, Integer> {


}
