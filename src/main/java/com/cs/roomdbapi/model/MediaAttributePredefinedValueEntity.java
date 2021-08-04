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
@Table(name = "media_attribute_predefined_value")
public class MediaAttributePredefinedValueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "media_attribute_type_id")
    private Integer mediaAttributeTypeId;

    @NotNull
    @Column
    private String value;

    @Column
    private String description;

    @Column
    @UpdateTimestamp
    private Timestamp last_update;

}
