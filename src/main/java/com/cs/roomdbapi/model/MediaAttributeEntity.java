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
@Table(name = "media_attribute")
public class MediaAttributeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "media_id")
    private Integer mediaId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_attribute_type_id")
    private MediaAttributeTypeEntity mediaAttributeType;

    @NotNull
    @Column
    private String value;

    @Column
    private String dimension;

    @Column
    @UpdateTimestamp
    private Timestamp last_update;

}
