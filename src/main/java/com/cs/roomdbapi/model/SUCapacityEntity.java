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
@Table(name = "sellable_unit_capacity")
public class SUCapacityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "sellable_unit_id")
    private Integer sellableUnitId;

    @NotNull
    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "is_blockout")
    private Boolean isBlockout;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    @JoinColumn(name = "time_range_id")
    private TimeRangeEntity timeRange;

    @Column
    @UpdateTimestamp
    private Timestamp last_update;

}
