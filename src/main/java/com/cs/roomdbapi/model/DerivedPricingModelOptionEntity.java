package com.cs.roomdbapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "derived_pricing_model_options")
public class DerivedPricingModelOptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "derived_pricing_model_id")
    private Integer derivedPricingModelId;

    @Column(name = "number_of_persons")
    private Integer numberOfPersons;

    @Column(name = "derive_percentage")
    private BigDecimal derivePercentage;

    @Column(name = "derive_price")
    private BigDecimal derivePrice;

    @Column
    @UpdateTimestamp
    private Timestamp last_update;

}
