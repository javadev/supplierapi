package com.cs.roomdbapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "derived_pricing_model")
public class DerivedPricingModelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "standard_occupancy")
    private Integer standardOccupancy;

    @Column(name = "standard_price")
    private BigDecimal standardPrice;

    @Column(name = "min_age")
    private Integer minAge;

    @Column(name = "max_age")
    private Integer maxAge;

    @OneToMany(
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "derived_pricing_model_id")
    private List<DerivedPricingModelOptionEntity> options;

    @Column
    @UpdateTimestamp
    private Timestamp last_update;

}
