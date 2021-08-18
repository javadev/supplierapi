package com.cs.roomdbapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "point_of_interest")
public class PointOfInterestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private PropertyEntity property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_code_id")
    private CategoryCodeEntity categoryCode;

    @Column
    private String name;

    @Column
    private BigDecimal distance;

    @Column(name = "distance_unit")
    private String distanceUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id")
    private LanguageEntity language;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "point_of_interest_x_description",
            joinColumns = @JoinColumn(name = "point_of_interest_id"),
            inverseJoinColumns = @JoinColumn(name = "description_id"))
    Set<DescriptionEntity> descriptions;

    @Column
    @UpdateTimestamp
    private Timestamp last_update;

}
