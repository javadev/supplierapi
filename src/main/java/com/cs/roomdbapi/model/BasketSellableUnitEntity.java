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
@Table(name = "basket_x_sellable_unit")
public class BasketSellableUnitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "basket_id")
    private Integer basketId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sellable_unit_id")
    private SellableUnitEntity sellableUnit;

    @NotNull
    @Column
    private Integer quantity;

    @Column(name = "consecutive_over_time")
    private Boolean consecutiveOverTime;

    @Column
    @UpdateTimestamp
    private Timestamp last_update;

}
