package com.cs.roomdbapi.repository;

import com.cs.roomdbapi.model.NotificationSupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NotificationSupplierRepository extends JpaRepository<NotificationSupplierEntity, Integer> {

    @Transactional(readOnly = true)
    List<NotificationSupplierEntity> findTop20ByStatusAndIsSendFalseAndAndIsEmailSendFalseOrderByLastUpdateAsc(short status);

}
