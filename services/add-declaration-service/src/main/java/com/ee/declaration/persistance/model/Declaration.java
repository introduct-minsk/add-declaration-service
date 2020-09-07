package com.ee.declaration.persistance.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(
        name = "declaration",
        indexes = {
                @Index(columnList = "serviceProviderId"),
                @Index(columnList = "serviceDeclarationId"),
                @Index(columnList = "serviceProviderId,serviceDeclarationId")
        },
        uniqueConstraints = {@UniqueConstraint(columnNames = {"serviceProviderId", "serviceDeclarationId"})}
)
@EqualsAndHashCode(of = "id")
public class Declaration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @Column(nullable = false, length = 100)
    private String serviceProviderId;

    @Column(nullable = false, length = 40)
    private String serviceDeclarationId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "declaration_id")
    private List<Name> name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "declaration_id")
    private List<Description> description;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "declaration_id")
    private List<TechnicalDescription> technicalDescription;

    @Column(nullable = false)
    private Integer consentMaxDurationSeconds;

    private Boolean needSignature = false;

    private LocalDateTime validUntil;

    private Integer maxCacheSeconds;

}
