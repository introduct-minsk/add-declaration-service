package com.ee.declaration.persistance.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.persistence.*;
import java.util.Locale;

@Data
@MappedSuperclass
@EqualsAndHashCode(of = "id")
public abstract class TranslatableString {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @Column(nullable = false)
    private Locale locale;

    @Column(nullable = false)
    private String value;
}
