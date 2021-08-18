package com.cs.roomdbapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "media")
public class MediaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private PropertyEntity property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_type_id")
    private MediaTypeEntity mediaType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "license_type_id")
    private LicenseTypeEntity licenseType;

    @Column(name = "is_main")
    private Boolean isMain;

    @Column(name = "is_logo")
    private Boolean isLogo;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column
    private String url;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    @JoinColumn(name = "media_id")
    private List<MediaTagEntity> tags;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "media_x_description",
            joinColumns = @JoinColumn(name = "media_id"),
            inverseJoinColumns = @JoinColumn(name = "description_id"))
    Set<DescriptionEntity> descriptions;

    @Column
    @UpdateTimestamp
    private Timestamp last_update;

}
