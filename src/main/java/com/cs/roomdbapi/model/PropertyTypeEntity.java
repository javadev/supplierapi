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
@Table(name = "property_type")
public class PropertyTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column
    private String code;

    @NotNull
    @Column
    private String name;

    @NotNull
    @Column(name = "code_source")
    private CodeSource codeSource;

    @Column(name = "alternative_name")
    private String alternativeName;

    @Column
    @UpdateTimestamp
    private Timestamp last_update;

}
