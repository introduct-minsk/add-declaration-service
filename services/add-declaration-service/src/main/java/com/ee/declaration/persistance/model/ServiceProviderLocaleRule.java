package com.ee.declaration.persistance.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Locale;

@Entity
@Data
@Table(
        indexes = {
                @Index(columnList = "serviceProviderId")
        },
        uniqueConstraints = {@UniqueConstraint(columnNames = {"serviceProviderId", "locale"})}
)
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class ServiceProviderLocaleRule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 100)
    private String serviceProviderId;

    @Column(nullable = false)
    private Locale locale;

}
