package com.cs.roomdbapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column
    private String name;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private PropertyEntity property;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "basket_id")
    private BasketEntity basket;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rate_plan_id")
    private RatePlanEntity ratePlan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pricing_model_id")
    private PricingModelEntity pricingModel;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_x_time_range",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "time_range_id"))
    Set<TimeRangeEntity> timeRanges;

    @Column
    @UpdateTimestamp
    private Timestamp last_update;

}
