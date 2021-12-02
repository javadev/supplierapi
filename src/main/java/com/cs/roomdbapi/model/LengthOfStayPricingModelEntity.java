package com.cs.roomdbapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "length_of_stay_pricing_model")
public class LengthOfStayPricingModelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "length_of_stay_pricing_model_id")
    private List<LengthOfStayPricingModelOptionEntity> options;

    @Column
    @UpdateTimestamp
    private Timestamp last_update;

}
