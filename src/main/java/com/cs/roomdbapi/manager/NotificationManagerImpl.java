package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Notification;
import com.cs.roomdbapi.dto.Supplier;
import com.cs.roomdbapi.exception.ResourceNotFoundException;
import com.cs.roomdbapi.mapper.NotificationMapper;
import com.cs.roomdbapi.model.NotificationEntity;
import com.cs.roomdbapi.model.NotificationSupplierEntity;
import com.cs.roomdbapi.repository.NotificationRepository;
import com.cs.roomdbapi.repository.NotificationSupplierRepository;
import com.cs.roomdbapi.restclient.RestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.cs.roomdbapi.utilities.AppUtils.ID;
import static com.cs.roomdbapi.utilities.AppUtils.NOTIFICATION;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationManagerImpl implements NotificationManager {

    private final NotificationRepository notificationRepository;

    private final NotificationSupplierRepository notificationSupplierRepository;

    private final RestClient restClient;

    private final SupplierManager supplierManager;

    @Override
    public Notification getById(Integer id) {
        NotificationEntity entity = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOTIFICATION, ID, id));

        return NotificationMapper.MAPPER.toDTO(entity);
    }

    @Override
    public void addBatchNotifications(@NonNull String entityName, @NonNull Integer entityId) {

        NotificationEntity toSave = new NotificationEntity(entityName, entityId);

        NotificationEntity entity = notificationRepository.save(toSave);
        Notification notification = NotificationMapper.MAPPER.toDTO(entity);

        List<Supplier> suppliers = supplierManager.getSuppliersActiveWithWebhook();
        List<Integer> processedSuppliers = new ArrayList<>();
        suppliers.forEach(s -> {
            NotificationSupplierEntity nse = new NotificationSupplierEntity(s.getId(), notification.getId());
            notificationSupplierRepository.save(nse);

            processedSuppliers.add(s.getId());
        });

        log.info("Notification '{}' scheduled for suppliers: '{}'", toSave.toString(), processedSuppliers.toString());
    }


    public boolean sendTestNotification(String supplierName) {
        Notification testNotification = new Notification(0, "TestEntity", 123, new Timestamp(System.currentTimeMillis()));

        Supplier supplier = supplierManager.getByName(supplierName);
        String url = supplier.getWebhook();

        return restClient.postNotification(url, testNotification);
    }

}
