package com.cs.roomdbapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "property_info_x_language")
public class PropertyInfoLanguageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "property_info_id")
    private Integer propertyInfoId;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private LanguageEntity language;

    @Column(name = "is_main")
    private Boolean isMain;

    @Column
    @UpdateTimestamp
    private Timestamp last_update;

}
