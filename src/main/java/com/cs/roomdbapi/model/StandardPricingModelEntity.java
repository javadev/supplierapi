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
@Table(name = "standard_pricing_model")
public class StandardPricingModelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "maximum_persons_price")
    private BigDecimal maximumPersonsPrice;

    @Column(name = "single_person_price")
    private BigDecimal singlePersonPrice;

    @Column
    @UpdateTimestamp
    private Timestamp last_update;

}
