package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<EmailEntity, Integer> {


}
