package com.cs.roomdbapi.model;

import com.cs.roomdbapi.utilities.PropertyTypeSource;
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
@Table(name = "property_type")
public class PropertyTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String code;

    @Column
    private String name;

    @Column(name = "code_source")
    private PropertyTypeSource codeSource;

    @Column(name = "alternative_name")
    private String alternativeName;

    @Column
    @UpdateTimestamp
    private Timestamp last_update;

}
