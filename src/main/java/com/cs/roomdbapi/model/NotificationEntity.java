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
@Table(name = "notification")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "entity_name")
    private String entityName;

    @NotNull
    @Column(name = "entity_id")
    private Integer entityId;

    @Column
    @UpdateTimestamp
    private Timestamp last_update;

    public NotificationEntity(@NotNull String entityName, @NotNull Integer entityId) {
        this.entityName = entityName;
        this.entityId = entityId;
    }
}
