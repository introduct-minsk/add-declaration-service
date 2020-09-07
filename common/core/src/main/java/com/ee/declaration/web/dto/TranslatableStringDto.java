package com.ee.declaration.web.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public abstract class TranslatableStringDto {

    @Valid
    @NotNull
    private LocaleDto locale;

    @NotEmpty
    private String value;

}
