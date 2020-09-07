package com.ee.declaration.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Schema(description = "Deklareeritava teenuse nimi. Võib sisaldada tõlget mitmes keeles.")
public class NameDto extends TranslatableStringDto {

    @Override
    @Schema(required = true, description = "Deklareeritava teenuse nimi valitud keeles.")
    public @NotEmpty @Size(max = 100) String getValue() {
        return super.getValue();
    }
}
