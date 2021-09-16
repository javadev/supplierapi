package com.cs.roomdbapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sellable_unit")
public class SellableUnitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private PropertyEntity property;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "sellable_unit_x_name",
            joinColumns = @JoinColumn(name = "sellable_unit_id"),
            inverseJoinColumns = @JoinColumn(name = "name_id"))
    List<NameEntity> names;

    @Column
    private Boolean limited;

    @Column(name = "sold_over_time")
    private Boolean soldOverTime;

    @Column(name = "base_price")
    private BigDecimal basePrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private SellableUnitTypeEntity sellableUnitType;

    @Column(name = "time_type")
    private TimeType timeType;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "sellable_unit_x_description",
            joinColumns = @JoinColumn(name = "sellable_unit_id"),
            inverseJoinColumns = @JoinColumn(name = "description_id"))
    List<DescriptionEntity> descriptions;   // TODO implement API to add/remove/update descriptions

    @Column
    @UpdateTimestamp
    private Timestamp last_update;

}
