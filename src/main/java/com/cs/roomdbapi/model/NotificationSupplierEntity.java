package com.cs.roomdbapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(NotificationSupplierId.class)
@Table(name = "supplier_x_notification")
public class NotificationSupplierEntity {

    @Id
    @NotNull
    @Column(name = "supplier_id")
    private Integer supplierId;

    @Id
    @NotNull
    @Column(name = "notification_id")
    private Integer notificationId;

    @NotNull
    @Column(name = "retry_count")
    private Integer retryCount;

    @Column(name = "is_send")
    private Boolean isSend;

    @Column(name = "is_email_send")
    private Boolean isEmailSend;

    @NotNull
    private short status;

    @Column(name = "last_update")
    @UpdateTimestamp
    private Timestamp lastUpdate;

    public NotificationSupplierEntity(@NotNull Integer supplierId, @NotNull Integer notificationId) {
        this.supplierId = supplierId;
        this.notificationId = notificationId;
        this.retryCount = 0;
        this.isSend = false;
        this.isEmailSend = false;
    }
}
