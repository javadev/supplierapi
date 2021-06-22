package com.cs.roomdbapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "language")
public class LanguageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String code;

    @Column
    private String name;

    @Column(name = "native_name")
    private String nativeName;

    @Column(name = "code_2t")
    private String code2T;

    @Column(name = "code_2b")
    private String code2B;

    @Column(name = "code_3")
    private String code3;

    @Column
    @UpdateTimestamp
    private Timestamp last_update;

}
