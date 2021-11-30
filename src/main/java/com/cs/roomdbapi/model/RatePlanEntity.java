package com.cs.roomdbapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rate_plan")
public class RatePlanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private PropertyEntity property;

    @NotNull
    @Column
    private String name;

    @OneToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "parent_id")
    private RatePlanEntity parent;

    @Column(name = "stop_sell")
    private Boolean stopSell;

    @Column(name = "closed_to_arrival")
    private Boolean closedToArrival;

    @Column(name = "closed_to_departure")
    private Boolean closedToDeparture;

    @Column(name = "min_length_of_stay")
    private Integer minLengthOfStay;

    @Column(name = "max_length_of_stay")
    private Integer maxLengthOfStay;

    @Column(name = "payment_policy_id")
    private Integer paymentPolicy;

    @Column(name = "cancellation_policy_id")
    private Integer cancellationPolicy;

    @Column(name = "booking_policy_id")
    private Integer bookingPolicy;

    @Column(name = "policy_info_id")
    private Integer policyInfo;

    @Column(name = "pet_policy_id")
    private Integer petPolicy;

    @Column(name = "commission")
    private BigDecimal commission;

    @Column
    @UpdateTimestamp
    private Timestamp last_update;

}
