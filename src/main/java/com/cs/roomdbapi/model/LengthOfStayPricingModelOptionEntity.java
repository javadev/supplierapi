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
@Table(name = "length_of_stay_pricing_model_options")
public class LengthOfStayPricingModelOptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "length_of_stay_pricing_model_id")
    private Integer lengthOfStayPricingModelId;

    @Column(name = "number_of_days")
    private Integer numberOfDays;

    @Column(name = "number_of_months")
    private Integer numberOfMonths;

    @Column(name = "price")
    private BigDecimal price;

    @Column
    @UpdateTimestamp
    private Timestamp last_update;

}
