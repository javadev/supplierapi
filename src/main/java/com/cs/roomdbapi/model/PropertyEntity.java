package com.cs.roomdbapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "property")
public class PropertyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "supplier_property_id")
    private String supplierPropertyId;

    @Column
    private String code;

    @Column
    private String name;

    @Column(name = "alternative_name")
    private String alternativeName;

    @Column
    private String status;

    @Column(name = "for_testing")
    private Boolean forTesting = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "home_currency_id")
    private CurrencyEntity homeCurrency;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "property_x_phone",
            joinColumns = @JoinColumn(name = "property_id"),
            inverseJoinColumns = @JoinColumn(name = "phone_id"))
    List<PhoneEntity> phones;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "property_x_email",
            joinColumns = @JoinColumn(name = "property_id"),
            inverseJoinColumns = @JoinColumn(name = "email_id"))
    List<EmailEntity> emails;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "property_x_description",
            joinColumns = @JoinColumn(name = "property_id"),
            inverseJoinColumns = @JoinColumn(name = "description_id"))
    List<DescriptionEntity> descriptions;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private SupplierEntity supplier;

    @Column
    @UpdateTimestamp
    private Timestamp last_update;

}
