package com.ee.declaration.util;

import com.ee.declaration.common.properties.LocaleProperty;
import com.ee.declaration.persistance.model.Declaration;
import com.ee.declaration.persistance.model.Description;
import com.ee.declaration.persistance.model.Name;
import com.ee.declaration.persistance.model.TechnicalDescription;
import com.ee.declaration.web.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

@Component
public final class DeclarationGenerator {

    @Autowired
    private LocaleProperty localeProperty;

    public Declaration randomDeclaration() {
        return randomDeclaration(localeProperty.getLocales());
    }

    public Declaration randomDeclaration(Collection<LocaleProperty.Local> locales) {
        List<Name> names = locales.stream()
                .map(local -> new Locale(local.getLanguage(), local.getCountry()))
                .map(this::randomName)
                .collect(Collectors.toList());

        List<Description> descriptions = locales.stream()
                .map(local -> new Locale(local.getLanguage(), local.getCountry()))
                .map(this::randomDescription)
                .collect(Collectors.toList());

        List<TechnicalDescription> technicalDescriptions = locales.stream()
                .map(local -> new Locale(local.getLanguage(), local.getCountry()))
                .map(this::randomTechnicalDescription)
                .collect(Collectors.toList());

        Declaration declaration = new Declaration();
        declaration.setServiceProviderId(randomAlphabetic(100));
        declaration.setServiceDeclarationId(randomAlphabetic(40));
        declaration.setName(names);
        declaration.setDescription(descriptions);
        declaration.setTechnicalDescription(technicalDescriptions);
        declaration.setConsentMaxDurationSeconds(Integer.valueOf(randomNumeric(3)));
        declaration.setNeedSignature(ThreadLocalRandom.current().nextInt(0, 1) == 0);
        declaration.setValidUntil(LocalDateTime.now().plusMonths(Integer.parseInt(randomNumeric(1))));
        declaration.setMaxCacheSeconds(Integer.valueOf(randomNumeric(3)));
        return declaration;
    }

    public Name randomName(Locale locale) {
        Name name = new Name();
        name.setLocale(locale);
        name.setValue(randomAlphabetic(100));
        return name;
    }

    public Description randomDescription(Locale locale) {
        Description description = new Description();
        description.setLocale(locale);
        description.setValue(randomAlphabetic(100));
        return description;
    }

    public TechnicalDescription randomTechnicalDescription(Locale locale) {
        TechnicalDescription technicalDescription = new TechnicalDescription();
        technicalDescription.setLocale(locale);
        technicalDescription.setValue(randomAlphabetic(100));
        return technicalDescription;
    }

    public DeclarationDto randomDeclarationDto() {
        return randomDeclarationDto(localeProperty.getLocales());
    }

    public DeclarationDto randomDeclarationDto(Collection<LocaleProperty.Local> locales) {
        List<NameDto> names = locales.stream()
                .map(local -> LocaleDto.builder().language(local.getLanguage()).country(local.getCountry()).build())
                .map(this::randomNameDto)
                .collect(Collectors.toList());

        List<DescriptionDto> descriptions = locales.stream()
                .map(local -> LocaleDto.builder().language(local.getLanguage()).country(local.getCountry()).build())
                .map(this::randomDescriptionDto)
                .collect(Collectors.toList());

        List<TechnicalDescriptionDto> technicalDescriptions = locales.stream()
                .map(local -> LocaleDto.builder().language(local.getLanguage()).country(local.getCountry()).build())
                .map(this::randomTechnicalDescriptionDto)
                .collect(Collectors.toList());

        return DeclarationDto.builder()
                .serviceProviderId(randomAlphabetic(100))
                .serviceDeclarationId(randomAlphabetic(40))
                .name(names)
                .description(descriptions)
                .technicalDescription(technicalDescriptions)
                .consentMaxDurationSeconds(Integer.valueOf(randomNumeric(3)))
                .needSignature(ThreadLocalRandom.current().nextInt(0, 1) == 0)
                .validUntil(LocalDateTime.now().plusMonths(1))
                .maxCacheSeconds(Integer.valueOf(randomNumeric(3)))
                .build();
    }

    public NameDto randomNameDto(Locale locale) {
        return randomNameDto(LocaleDto.builder()
                .language(locale.getLanguage())
                .country(locale.getCountry())
                .build());
    }

    public NameDto randomNameDto(LocaleDto localeDto) {
        NameDto nameDto = new NameDto();
        nameDto.setLocale(localeDto);
        nameDto.setValue(randomAlphabetic(100));
        return nameDto;
    }

    public DescriptionDto randomDescriptionDto(Locale locale) {
        return randomDescriptionDto(LocaleDto.builder()
                .language(locale.getLanguage())
                .country(locale.getCountry())
                .build());
    }

    public DescriptionDto randomDescriptionDto(LocaleDto localeDto) {
        DescriptionDto descriptionDto = new DescriptionDto();
        descriptionDto.setLocale(localeDto);
        descriptionDto.setValue(randomAlphabetic(100));
        return descriptionDto;
    }

    public TechnicalDescriptionDto randomTechnicalDescriptionDto(Locale locale) {
        return randomTechnicalDescriptionDto(LocaleDto.builder()
                .language(locale.getLanguage())
                .country(locale.getCountry())
                .build());
    }

    public TechnicalDescriptionDto randomTechnicalDescriptionDto(LocaleDto localeDto) {
        TechnicalDescriptionDto technicalDescriptionDto = new TechnicalDescriptionDto();
        technicalDescriptionDto.setLocale(localeDto);
        technicalDescriptionDto.setValue(randomAlphabetic(100));
        return technicalDescriptionDto;
    }

}
