package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.model.NotificationSupplierEntity;
import com.cs.roomdbapi.repository.NotificationSupplierRepository;
import com.cs.roomdbapi.utilities.AppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationSupplierManagerImpl implements NotificationSupplierManager {

    private final NotificationSupplierRepository notificationSupplierRepository;

    @Override
    public List<NotificationSupplierEntity> findTop20ToProcess() {
        return notificationSupplierRepository.findTop20ByStatusAndIsSendFalseAndAndIsEmailSendFalseOrderByLastUpdateAsc(AppUtils.NOTIFICATION_SUPPLIER_STATUS_QUEUED);
    }

    @Override
    public NotificationSupplierEntity save(NotificationSupplierEntity entity) {
        return notificationSupplierRepository.save(entity);
    }

}
