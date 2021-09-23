package com.cs.roomdbapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "availability")
public class AvailabilityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "sellable_unit_id")
    private Integer sellableUnitId;

    @NotNull
    @Column(name = "count_available")
    private Integer countAvailable;

    @Column
    private LocalDate date;

    @Column(name = "time_segment")
    private LocalTime timeSegment;

    @Column
    @UpdateTimestamp
    private Timestamp last_update;

}
