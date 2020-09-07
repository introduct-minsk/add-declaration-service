package com.ee.declaration.mapper;

import com.ee.declaration.persistance.model.Description;
import com.ee.declaration.persistance.model.Name;
import com.ee.declaration.persistance.model.TechnicalDescription;
import com.ee.declaration.web.dto.DescriptionDto;
import com.ee.declaration.web.dto.LocaleDto;
import com.ee.declaration.web.dto.NameDto;
import com.ee.declaration.web.dto.TechnicalDescriptionDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Locale;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
class TranslatableStringMapperTest {

    @Autowired
    private NameMapper nameMapper;
    @Autowired
    private DescriptionMapper descriptionMapper;
    @Autowired
    private TechnicalDescriptionMapper technicalDescriptionMapper;

    @Test
    void givenUsLocale_whenNameMapping_thenOk() {
        assertNameOkMapping(Locale.US);
    }

    @Test
    void givenFranceLocale_whenNameMapping_thenOk() {
        assertNameOkMapping(Locale.FRANCE);
    }

    @Test
    void givenItalyLocale_whenNameMapping_thenOk() {
        assertNameOkMapping(Locale.ITALY);
    }

    @Test
    void givenUsLocale_whenDescriptionMapping_thenOk() {
        assertDescriptionOkMapping(Locale.US);
    }

    @Test
    void givenFranceLocale_whenDescriptionMapping_thenOk() {
        assertDescriptionOkMapping(Locale.FRANCE);
    }

    @Test
    void givenItalyLocale_whenDescriptionMapping_thenOk() {
        assertDescriptionOkMapping(Locale.ITALY);
    }

    @Test
    void givenUsLocale_whenTechnicalDescriptionMapping_thenOk() {
        assertTechnicalDescriptionOkMapping(Locale.US);
    }

    @Test
    void givenFranceLocale_whenTechnicalDescriptionMapping_thenOk() {
        assertTechnicalDescriptionOkMapping(Locale.FRANCE);
    }

    @Test
    void givenItalyLocale_whenTechnicalDescriptionMapping_thenOk() {
        assertTechnicalDescriptionOkMapping(Locale.ITALY);
    }

    private void assertNameOkMapping(Locale locale) {
        LocaleDto localeDto = LocaleDto.builder()
                .language(locale.getLanguage())
                .country(locale.getCountry())
                .build();

        NameDto nameDto = new NameDto();
        nameDto.setValue(randomAlphabetic(100));
        nameDto.setLocale(localeDto);

        Name name = nameMapper.toDomain(nameDto);

        assertNotNull(name);
        assertEquals(nameDto.getValue(), name.getValue());
        assertEquals(locale, name.getLocale());
    }

    private void assertDescriptionOkMapping(Locale locale) {
        LocaleDto localeDto = LocaleDto.builder()
                .language(locale.getLanguage())
                .country(locale.getCountry())
                .build();

        DescriptionDto descriptionDto = new DescriptionDto();
        descriptionDto.setValue(randomAlphabetic(100));
        descriptionDto.setLocale(localeDto);

        Description description = descriptionMapper.toDomain(descriptionDto);

        assertNotNull(description);
        assertEquals(descriptionDto.getValue(), description.getValue());
        assertEquals(locale, description.getLocale());
    }

    private void assertTechnicalDescriptionOkMapping(Locale locale) {
        LocaleDto localeDto = LocaleDto.builder()
                .language(locale.getLanguage())
                .country(locale.getCountry())
                .build();

        TechnicalDescriptionDto technicalDescriptionDto = new TechnicalDescriptionDto();
        technicalDescriptionDto.setValue(randomAlphabetic(100));
        technicalDescriptionDto.setLocale(localeDto);

        TechnicalDescription technicalDescription = technicalDescriptionMapper.toDomain(technicalDescriptionDto);

        assertNotNull(technicalDescription);
        assertEquals(technicalDescriptionDto.getValue(), technicalDescription.getValue());
        assertEquals(locale, technicalDescription.getLocale());
    }


}