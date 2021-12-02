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
@Table(name = "phone")
public class PhoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column
    private String extension;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phone_type_id")
    private PhoneTypeEntity phoneType;

    @Column(name = "is_validated")
    private Boolean isValidated = false;

    @Column
    @UpdateTimestamp
    private Timestamp last_update;

}
