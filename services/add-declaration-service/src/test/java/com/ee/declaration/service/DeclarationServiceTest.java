package com.ee.declaration.service;

import com.ee.declaration.exception.DuplicateDeclarationException;
import com.ee.declaration.exception.InvalidLocaleException;
import com.ee.declaration.persistance.dao.ServiceProviderLocaleRuleRepository;
import com.ee.declaration.persistance.model.Declaration;
import com.ee.declaration.persistance.model.ServiceProviderLocaleRule;
import com.ee.declaration.persistance.model.TranslatableString;
import com.ee.declaration.util.DeclarationGenerator;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Locale;
import java.util.Set;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class DeclarationServiceTest {

    @Autowired
    private DeclarationGenerator declarationGenerator;

    @Autowired
    private DeclarationService service;

    @MockBean
    private ServiceProviderLocaleRuleRepository serviceProviderLocaleRuleRepository;

    @Test
    void whenServiceProviderIdNull_thenExceptionThrown() {
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            Declaration declaration = declarationGenerator.randomDeclaration();
            declaration.setServiceProviderId(null);
            service.add(declaration);
        });

        assertNotNull(exception.getRootCause());
        assertTrue(exception.getRootCause().getMessage().contains("NULL not allowed for column \"SERVICE_PROVIDER_ID\""));
    }

    @Test
    void whenServiceProviderIdLengthIs100_thenOk() {
        Declaration declaration = declarationGenerator.randomDeclaration();
        declaration.setServiceProviderId(randomAlphabetic(100));
        service.add(declaration);
    }

    @Test
    void whenServiceProviderIdLengthLargerThan100_thenExceptionThrown() {
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            Declaration declaration = declarationGenerator.randomDeclaration();
            declaration.setServiceProviderId(randomAlphabetic(101));
            service.add(declaration);
        });

        assertNotNull(exception.getRootCause());
        assertTrue(exception.getRootCause().getMessage().contains("Value too long for column \"SERVICE_PROVIDER_ID VARCHAR(100)\""));
    }

    @Test
    void whenServiceDeclarationIdNull_thenExceptionThrown() {
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            Declaration declaration = declarationGenerator.randomDeclaration();
            declaration.setServiceDeclarationId(null);
            service.add(declaration);
        });

        assertNotNull(exception.getRootCause());
        assertTrue(exception.getRootCause().getMessage().contains("NULL not allowed for column \"SERVICE_DECLARATION_ID\""));
    }

    @Test
    void whenServiceDeclarationIdLengthIs40_thenOk() {
        Declaration declaration = declarationGenerator.randomDeclaration();
        declaration.setServiceDeclarationId(randomAlphabetic(40));
        service.add(declaration);
    }

    @Test
    void whenServiceDeclarationIdLengthLargerThan40_thenExceptionThrown() {
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            Declaration declaration = declarationGenerator.randomDeclaration();
            declaration.setServiceDeclarationId(randomAlphabetic(41));
            service.add(declaration);
        });

        assertNotNull(exception.getRootCause());
        assertTrue(exception.getRootCause().getMessage().contains("Value too long for column \"SERVICE_DECLARATION_ID VARCHAR(40)\""));
    }

    @Test
    void whenNameValueNull_thenExceptionThrown() {
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            Declaration declaration = declarationGenerator.randomDeclaration();
            declaration.getName().forEach(name -> name.setValue(null));
            service.add(declaration);
        });

        assertNotNull(exception.getRootCause());
        assertTrue(exception.getRootCause().getMessage().contains("NULL not allowed for column \"VALUE\""));
    }

    @Test
    void whenNameValueLengthIs100_thenOk() {
        Declaration declaration = declarationGenerator.randomDeclaration();
        declaration.getName().forEach(name -> name.setValue(randomAlphabetic(100)));
        service.add(declaration);
    }

    @Test
    void whenNameValueLengthLargerThan100_thenExceptionThrown() {
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            Declaration declaration = declarationGenerator.randomDeclaration();
            declaration.getName().forEach(name -> name.setValue(randomAlphabetic(101)));
            service.add(declaration);
        });

        assertNotNull(exception.getRootCause());
        assertTrue(exception.getRootCause().getMessage().contains("Value too long for column \"VALUE VARCHAR(100)\""));
    }

    @Test
    void whenDescriptionValueNull_thenExceptionThrown() {
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            Declaration declaration = declarationGenerator.randomDeclaration();
            declaration.getDescription().forEach(description -> description.setValue(null));
            service.add(declaration);
        });

        assertNotNull(exception.getRootCause());
        assertTrue(exception.getRootCause().getMessage().contains("NULL not allowed for column \"VALUE\""));
    }

    @Test
    void whenDescriptionLengthIs255_thenOk() {
        Declaration declaration = declarationGenerator.randomDeclaration();
        declaration.getDescription().forEach(description -> description.setValue(randomAlphabetic(255)));
        service.add(declaration);
    }

    @Test
    void whenDescriptionLengthLargerThan255_thenExceptionThrown() {
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            Declaration declaration = declarationGenerator.randomDeclaration();
            declaration.getDescription().forEach(description -> description.setValue(randomAlphabetic(256)));
            service.add(declaration);
        });

        assertNotNull(exception.getRootCause());
        assertTrue(exception.getRootCause().getMessage().contains("Value too long for column \"VALUE VARCHAR(255)\""));
    }

    @Test
    void whenTechnicalDescriptionValueNull_thenExceptionThrown() {
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            Declaration declaration = declarationGenerator.randomDeclaration();
            declaration.getTechnicalDescription().forEach(technicalDescription -> technicalDescription.setValue(null));
            service.add(declaration);
        });

        assertNotNull(exception.getRootCause());
        assertTrue(exception.getRootCause().getMessage().contains("NULL not allowed for column \"VALUE\""));
    }

    @Test
    void whenTechnicalDescriptionValueLengthIs255_thenOk() {
        Declaration declaration = declarationGenerator.randomDeclaration();
        declaration.getTechnicalDescription()
                .forEach(technicalDescription -> technicalDescription.setValue(randomAlphabetic(255)));
        service.add(declaration);
    }

    @Test
    void whenTechnicalDescriptionValueLengthLargerThan256_thenExceptionThrown() {
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            Declaration declaration = declarationGenerator.randomDeclaration();
            declaration.getTechnicalDescription()
                    .forEach(technicalDescription -> technicalDescription.setValue(randomAlphabetic(256)));
            service.add(declaration);
        });

        assertNotNull(exception.getRootCause());
        assertTrue(exception.getRootCause().getMessage().contains("Value too long for column \"VALUE VARCHAR(255)\""));
    }

    @Test
    void whenConsentMaxDurationSecondsNull_thenExceptionThrown() {
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            Declaration declaration = declarationGenerator.randomDeclaration();
            declaration.setConsentMaxDurationSeconds(null);
            service.add(declaration);
        });

        assertNotNull(exception.getRootCause());
        assertTrue(exception.getRootCause().getMessage().contains("NULL not allowed for column \"CONSENT_MAX_DURATION_SECONDS\""));
    }

    @Test
    void whenDuplicateDeclaration_thenExceptionThrown() {
        Declaration firstDeclaration = declarationGenerator.randomDeclaration();
        service.add(firstDeclaration);

        assertThrows(DuplicateDeclarationException.class, () -> {
            Declaration secondDeclaration = declarationGenerator.randomDeclaration();
            secondDeclaration.setServiceProviderId(firstDeclaration.getServiceProviderId());
            secondDeclaration.setServiceDeclarationId(firstDeclaration.getServiceDeclarationId());
            service.add(secondDeclaration);
        });
    }

    @Test
    void whenAddDeclaration_thenOk() {
        Declaration declaration = declarationGenerator.randomDeclaration();
        service.add(declaration);
    }

    @Test
    void givenLocaleNotInList_whenAddDeclaration_thenOk() {
        Declaration declaration = declarationGenerator.randomDeclaration();

        declaration.getName().add(declarationGenerator.randomName(Locale.FRANCE));
        declaration.getDescription().add(declarationGenerator.randomDescription(Locale.FRANCE));
        declaration.getTechnicalDescription().add(declarationGenerator.randomTechnicalDescription(Locale.FRANCE));

        service.add(declaration);
    }

    @Test
    void givenLocaleInList_whenAddDeclarationWithoutGivenLocale_thenExceptionThrown() {
        Declaration declaration = declarationGenerator.randomDeclaration();

        ServiceProviderLocaleRule serviceProviderLocaleRule = new ServiceProviderLocaleRule();
        serviceProviderLocaleRule.setServiceProviderId(declaration.getServiceProviderId());
        serviceProviderLocaleRule.setLocale(Locale.ITALY);

        doReturn(Set.of(serviceProviderLocaleRule))
                .when(serviceProviderLocaleRuleRepository)
                .findAllByServiceProviderId(declaration.getServiceProviderId());

        assertThrows(InvalidLocaleException.class, () -> service.add(declaration));
    }

    @Test
    void givenDuplicateTranslationsForName_whenAddDeclaration_thenExceptionThrown() {
        Declaration declaration = declarationGenerator.randomDeclaration();

        declaration.getName()
                .stream()
                .findAny()
                .map(TranslatableString::getLocale)
                .map(declarationGenerator::randomName)
                .ifPresent(declaration.getName()::add);

        assertThrows(DataIntegrityViolationException.class, () -> service.add(declaration));
    }


    @Test
    void givenDuplicateTranslationsForDescription_whenAddDeclaration_thenExceptionThrown() {
        Declaration declaration = declarationGenerator.randomDeclaration();

        declaration.getDescription()
                .stream()
                .findAny()
                .map(TranslatableString::getLocale)
                .map(declarationGenerator::randomDescription)
                .ifPresent(declaration.getDescription()::add);

        assertThrows(DataIntegrityViolationException.class, () -> service.add(declaration));
    }

    @Test
    void givenDuplicateTranslationsForTechnicalDescription_whenAddDeclaration_thenExceptionThrown() {
        Declaration declaration = declarationGenerator.randomDeclaration();

        declaration.getTechnicalDescription()
                .stream()
                .findAny()
                .map(TranslatableString::getLocale)
                .map(declarationGenerator::randomTechnicalDescription)
                .ifPresent(declaration.getTechnicalDescription()::add);

        assertThrows(DataIntegrityViolationException.class, () -> service.add(declaration));
    }

}