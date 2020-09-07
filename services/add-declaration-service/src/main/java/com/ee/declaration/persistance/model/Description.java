package com.ee.declaration.persistance.model;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
        name = "declaration_description",
        indexes = {
                @Index(columnList = "declaration_id")
        },
        uniqueConstraints = {@UniqueConstraint(columnNames = {"declaration_id", "locale"})}
)
public class Description extends TranslatableString {

}
