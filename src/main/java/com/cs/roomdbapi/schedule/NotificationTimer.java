package com.cs.roomdbapi.schedule;

import com.cs.roomdbapi.dto.Notification;
import com.cs.roomdbapi.manager.NotificationManager;
import com.cs.roomdbapi.manager.NotificationSupplierManager;
import com.cs.roomdbapi.manager.SupplierManager;
import com.cs.roomdbapi.model.NotificationSupplierEntity;
import com.cs.roomdbapi.restclient.RestClient;
import com.cs.roomdbapi.utilities.AppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.TimerTask;

@RequiredArgsConstructor
@Slf4j
@Component
public class NotificationTimer extends TimerTask {

    private final NotificationSupplierManager notificationSupplierManager;

    private final NotificationManager notificationManager;

    private final SupplierManager supplierManager;

    private final RestClient restClient;

//    @Scheduled(cron = "*/20 * * * * *") // every 20 seconds
    @Scheduled(cron = "0 * * * * *") // every minute
    public void run() {
        List<NotificationSupplierEntity> list = notificationSupplierManager.findTop20ToProcess();

        if (list != null && !list.isEmpty()) {
            list.forEach(event -> {
                event.setStatus(AppUtils.NOTIFICATION_SUPPLIER_STATUS_IN_PROGRESS);
                event = notificationSupplierManager.save(event);

                Notification notification = notificationManager.getById(event.getNotificationId());

                if (event.getRetryCount() < 3) {

                    // Trying to send to webhook url
                    String url = supplierManager.getWebhookById(event.getSupplierId());
                    if (url == null || url.isBlank()) {
                        event.setStatus(AppUtils.NOTIFICATION_SUPPLIER_STATUS_FAILED_NO_URL);
                        notificationSupplierManager.save(event);

                        log.warn("Notification '{}' to Supplier '{}' failed NO webhook url.", event.getNotificationId(), event.getSupplierId());
                        return;
                    }

                    boolean success = restClient.postNotification(url, notification);

                    event.setRetryCount(event.getRetryCount() + 1);
                    if (success) {
                        event.setIsSend(true);
                        event.setStatus(AppUtils.NOTIFICATION_SUPPLIER_STATUS_FINISHED);
                    } else {
                        event.setStatus(AppUtils.NOTIFICATION_SUPPLIER_STATUS_QUEUED);
                    }
                    notificationSupplierManager.save(event);

                } else {
                    // TODO more than 3 retry, let's try send via email

                    String email = "";
                    if (email.isBlank()) {
                        event.setStatus(AppUtils.NOTIFICATION_SUPPLIER_STATUS_FAILED_NO_EMAIL);
                        notificationSupplierManager.save(event);

                        log.warn("Notification '{}' to Supplier '{}' failed NO email.", event.getNotificationId(), event.getSupplierId());
                        return;
                    }




                    boolean isOk = true; // result of email try

                    if (isOk) {
                        event.setIsEmailSend(true);
                        event.setStatus(AppUtils.NOTIFICATION_SUPPLIER_STATUS_FINISHED);
                    } else {
                        event.setStatus(AppUtils.NOTIFICATION_SUPPLIER_STATUS_FAILED);
                        log.warn("Notification '{}' to Supplier '{}' failed 3 times via webhook, and via email.", event.getNotificationId(), event.getSupplierId());
                    }
                    notificationSupplierManager.save(event);
                }

            });
        }
    }

}
