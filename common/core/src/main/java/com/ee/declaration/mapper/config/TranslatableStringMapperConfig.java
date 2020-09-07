package com.ee.declaration.mapper.config;

import com.ee.declaration.persistance.model.TranslatableString;
import com.ee.declaration.web.dto.LocaleDto;
import com.ee.declaration.web.dto.TranslatableStringDto;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Locale;

@MapperConfig
public interface TranslatableStringMapperConfig {

    @Mapping(source = "locale", target = "locale", qualifiedByName = "toLocale")
    void toDomain(TranslatableStringDto dto, @MappingTarget TranslatableString domain);

    @Named("toLocale")
    static Locale toLocale(LocaleDto localeDto) {
        return new Locale(localeDto.getLanguage(), localeDto.getCountry());
    }

}
