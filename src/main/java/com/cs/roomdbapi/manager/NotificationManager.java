package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Notification;
import org.springframework.lang.NonNull;

public interface NotificationManager {

    Notification getById(Integer id);

    void addBatchNotifications(@NonNull String entityName, @NonNull Integer entityId);

    boolean sendTestNotification(String supplierName);

}
