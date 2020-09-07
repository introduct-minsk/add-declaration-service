package com.ee.declaration.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Schema(description = "Teenuse deklaratsiooni kirjeldus. Võib sisaldada tõlget mitmes keeles.")
public class DescriptionDto extends TranslatableStringDto {

    @Override
    @Schema(required = true, description = "Teenuse deklaratsiooni kirjledus valitud keeles.")
    public @NotEmpty @Size(max = 255) String getValue() {
        return super.getValue();
    }
}
