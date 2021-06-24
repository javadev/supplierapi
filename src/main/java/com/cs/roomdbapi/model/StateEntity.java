package com.cs.roomdbapi.model;

import com.cs.roomdbapi.utilities.SubdivisionCategory;
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
@Table(name = "state")
public class StateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private CountryEntity country;

    @NotNull
    @Column
    private String code;

    @NotNull
    @Column
    private String name;

    @Column(name = "local_name")
    private String localName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private LanguageEntity language;

    @Column(name = "subdivision_category")
    private SubdivisionCategory subdivisionCategory;

    @Column
    @UpdateTimestamp
    private Timestamp last_update;

}
