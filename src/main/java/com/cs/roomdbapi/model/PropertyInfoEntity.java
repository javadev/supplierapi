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
@Table(name = "property_info")
public class PropertyInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "property_id")
    private Integer propertyId;

    @Column
    private String website;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    @JoinColumn(name = "geo_code_id")
    private GeoCodeEntity geoCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private BrandEntity brand;

    @Column(name = "taxpayer_id")
    private String taxpayerId;

    @Column
    private Integer capacity;

    @Column(name = "capacity_type")
    private CapacityType capacityType;

    @Column(name = "is_exist")
    private Boolean isExist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_type_id")
    private PropertyTypeEntity propertyType;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "property_info_x_address",
            joinColumns = @JoinColumn(name = "property_info_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    List<AddressEntity> addresses;

    @Column
    @UpdateTimestamp
    private Timestamp last_update;

}
