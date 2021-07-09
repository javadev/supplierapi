package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.model.NotificationSupplierEntity;

import java.util.List;

public interface NotificationSupplierManager {

    List<NotificationSupplierEntity> findTop20ToProcess();

    NotificationSupplierEntity save(NotificationSupplierEntity entity);

}
