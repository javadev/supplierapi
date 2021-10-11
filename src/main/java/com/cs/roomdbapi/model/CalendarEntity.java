package com.cs.roomdbapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "calendar")
public class CalendarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "sellable_unit_id")
    private Integer sellableUnitId;

    @Column
    private LocalDate date;

    @Column(name = "count_available")
    private Integer countAvailable;

    @Column
    private BigDecimal price;

    @Column(name = "min_los")
    private Integer minLOS;
    
    @Column(name = "max_los")
    private Integer maxLOS;

    @Column(name = "closed_for_sale")
    private Boolean closedForSale;

    @Column(name = "closed_for_arrival")
    private Boolean closedForArrival;

    @Column(name = "closed_for_departure")
    private Boolean closedForDeparture;

    @Column(name = "time_segment")
    private LocalTime timeSegment;

    @Column
    @UpdateTimestamp
    private Timestamp last_update;

}
