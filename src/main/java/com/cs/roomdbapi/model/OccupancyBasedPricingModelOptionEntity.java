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
@Table(name = "occupancy_based_pricing_model_options")
public class OccupancyBasedPricingModelOptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "occupancy_based_pricing_model_id")
    private Integer occupancyBasedPricingModelId;

    @Column(name = "number_of_persons")
    private Integer numberOfPersons;

    @Column(name = "price")
    private BigDecimal price;

    @Column
    @UpdateTimestamp
    private Timestamp last_update;

}
