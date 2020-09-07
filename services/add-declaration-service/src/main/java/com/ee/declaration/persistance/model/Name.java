package com.ee.declaration.persistance.model;

import javax.persistence.*;

@Entity
@Table(
        name = "declaration_name",
        indexes = {
                @Index(columnList = "declaration_id")
        },
        uniqueConstraints = {@UniqueConstraint(columnNames = {"declaration_id", "locale"})}
)
@AttributeOverride(column = @Column(name = "value", length = 100, nullable = false), name = "value")
public class Name extends TranslatableString {

}
