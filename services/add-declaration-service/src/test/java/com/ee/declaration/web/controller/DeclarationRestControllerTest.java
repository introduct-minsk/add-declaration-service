package com.ee.declaration.web.controller;

import com.ee.declaration.common.Headers;
import com.ee.declaration.persistance.dao.ServiceProviderLocaleRuleRepository;
import com.ee.declaration.persistance.model.ServiceProviderLocaleRule;
import com.ee.declaration.util.DeclarationGenerator;
import com.ee.declaration.web.dto.DeclarationDto;
import com.ee.declaration.web.dto.TranslatableStringDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Consumer;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class DeclarationRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DeclarationGenerator declarationGenerator;

    @MockBean
    private ServiceProviderLocaleRuleRepository serviceProviderLocaleRuleRepository;

    @Test
    void whenAddValidDeclaration_theOk() throws Exception {
        assertOk(addDeclaration(declarationGenerator.randomDeclarationDto()));
    }

    // serviceProviderId

    @Test
    void whenServiceProviderIdNull_thenInvalidRequest() throws Exception {
        String errorDescription = "Service provider id cannot be empty or Null";
        assertInvalidRequest(declarationDto -> declarationDto.setServiceProviderId(null), errorDescription);
    }

    @Test
    void whenServiceProviderIdEmpty_thenInvalidRequest() throws Exception {
        String errorDescription = "Service provider id cannot be empty or Null";
        assertInvalidRequest(declarationDto -> declarationDto.setServiceProviderId(""), errorDescription);
    }

    @Test
    void whenServiceProviderIdLengthIs100_thenOk() throws Exception {
        assertOk(declarationDto -> declarationDto.setServiceProviderId(randomAlphabetic(100)));
    }

    @Test
    void whenServiceProviderIdLengthLargerThan100_thenInvalidRequest() throws Exception {
        String errorDescription = "Service provider id must have size not larger than 100 characters";
        assertInvalidRequest(declarationDto -> declarationDto.setServiceProviderId(randomAlphabetic(101)),
                errorDescription);
    }

    // serviceDeclarationId

    @Test
    void whenServiceDeclarationIdNull_thenInvalidRequest() throws Exception {
        String errorDescription = "Service declaration id cannot be empty or Null";
        assertInvalidRequest(declarationDto -> declarationDto.setServiceDeclarationId(null), errorDescription);
    }

    @Test
    void whenServiceDeclarationIdEmpty_thenInvalidRequest() throws Exception {
        String errorDescription = "Service declaration id cannot be empty or Null";
        assertInvalidRequest(declarationDto -> declarationDto.setServiceDeclarationId(""), errorDescription);
    }

    @Test
    void whenServiceDeclarationIdLengthIs40_thenOk() throws Exception {
        assertOk(declarationDto -> declarationDto.setServiceDeclarationId(randomAlphabetic(40)));
    }

    @Test
    void whenServiceDeclarationIdLengthLargerThan40_thenInvalidRequest() throws Exception {
        String errorDescription = "Service declaration id must have size not larger than 40 characters";
        assertInvalidRequest(declarationDto ->
                declarationDto.setServiceDeclarationId(randomAlphabetic(41)), errorDescription);
    }

    // name

    @Test
    void whenNameNull_thenInvalidRequest() throws Exception {
        String errorDescription = "Declaration name cannot be empty or Null";
        assertInvalidRequest(declarationDto -> declarationDto.setName(null), errorDescription);
    }

    @Test
    void whenNameListEmpty_thenInvalidRequest() throws Exception {
        String errorDescription = "Declaration name cannot be empty or Null";
        assertInvalidRequest(declarationDto -> declarationDto.setName(List.of()), errorDescription);
    }

    @Test
    void whenNameValueNull_thenInvalidRequest() throws Exception {
        String errorDescription = "Declaration name translation cannot be empty or Null";
        assertInvalidRequest(declarationDto ->
                declarationDto.getName().forEach(nameDto -> nameDto.setValue(null)), errorDescription);
    }

    @Test
    void whenNameValueEmpty_thenInvalidRequest() throws Exception {
        String errorDescription = "Declaration name translation cannot be empty or Null";
        assertInvalidRequest(declarationDto ->
                declarationDto.getName().forEach(nameDto -> nameDto.setValue("")), errorDescription);
    }

    @Test
    void whenNameValueIs100_thenOk() throws Exception {
        assertOk(declarationDto -> declarationDto.getName()
                .forEach(nameDto -> nameDto.setValue(randomAlphabetic(100))));
    }

    @Test
    void whenNameValueLargerThan100_thenInvalidRequest() throws Exception {
        String errorDescription = "Declaration name translation must have size not larger than 100 characters";
        assertInvalidRequest(declarationDto -> declarationDto.getName()
                .forEach(nameDto -> nameDto.setValue(randomAlphabetic(101))), errorDescription);
    }

    @Test
    void whenNameLocaleNull_thenInvalidRequest() throws Exception {
        String errorDescription = "Declaration name must have locale";
        assertInvalidRequest(declarationDto -> declarationDto.getName()
                .forEach(nameDto -> nameDto.setLocale(null)), errorDescription);
    }

    // description

    @Test
    void whenDescriptionNull_thenInvalidRequest() throws Exception {
        String errorDescription = "Declaration description cannot be empty or Null";
        assertInvalidRequest(declarationDto -> declarationDto.setDescription(null), errorDescription);
    }

    @Test
    void whenDescriptionListEmpty_thenInvalidRequest() throws Exception {
        String errorDescription = "Declaration description cannot be empty or Null";
        assertInvalidRequest(declarationDto -> declarationDto.setDescription(List.of()), errorDescription);
    }

    @Test
    void whenDescriptionValueNull_thenInvalidRequest() throws Exception {
        String errorDescription = "Declaration description translation cannot be empty or Null";
        assertInvalidRequest(declarationDto -> declarationDto.getDescription()
                .forEach(descriptionDto -> descriptionDto.setValue(null)), errorDescription);
    }

    @Test
    void whenDescriptionValueEmpty_thenInvalidRequest() throws Exception {
        String errorDescription = "Declaration description translation cannot be empty or Null";
        assertInvalidRequest(declarationDto -> declarationDto.getDescription()
                .forEach(descriptionDto -> descriptionDto.setValue("")), errorDescription);
    }

    @Test
    void whenDescriptionValueIs255_thenOk() throws Exception {
        assertOk(declarationDto -> declarationDto.getDescription()
                .forEach(descriptionDto -> descriptionDto.setValue(randomAlphabetic(255))));
    }

    @Test
    void whenDescriptionValueLargerThan255_thenInvalidRequest() throws Exception {
        String errorDescription = "Declaration description translation must have size not larger than 255 characters";
        assertInvalidRequest(declarationDto -> declarationDto.getDescription()
                .forEach(descriptionDto -> descriptionDto.setValue(randomAlphabetic(256))), errorDescription);
    }

    @Test
    void whenDescriptionLocaleNull_thenInvalidRequest() throws Exception {
        String errorDescription = "Declaration description must have locale";
        assertInvalidRequest(declarationDto -> declarationDto.getDescription()
                .forEach(descriptionDto -> descriptionDto.setLocale(null)), errorDescription);
    }

    // technicalDescription

    @Test
    void whenTechnicalDescriptionNull_thenInvalidRequest() throws Exception {
        String errorDescription = "Declaration technical description cannot be empty or Null";
        assertInvalidRequest(declarationDto -> declarationDto.setTechnicalDescription(null), errorDescription);
    }

    @Test
    void whenTechnicalDescriptionListEmpty_thenInvalidRequest() throws Exception {
        String errorDescription = "Declaration technical description cannot be empty or Null";
        assertInvalidRequest(declarationDto -> declarationDto.setTechnicalDescription(List.of()), errorDescription);
    }

    @Test
    void whenTechnicalDescriptionValueNull_thenInvalidRequest() throws Exception {
        String errorDescription = "Declaration technical description translation cannot be empty or Null";
        assertInvalidRequest(declarationDto -> declarationDto.getTechnicalDescription()
                .forEach(technicalDescriptionDto -> technicalDescriptionDto.setValue(null)), errorDescription);
    }

    @Test
    void whenTechnicalDescriptionValueEmpty_thenInvalidRequest() throws Exception {
        String errorDescription = "Declaration technical description translation cannot be empty or Null";
        assertInvalidRequest(declarationDto -> declarationDto.getTechnicalDescription()
                .forEach(technicalDescriptionDto -> technicalDescriptionDto.setValue("")), errorDescription);
    }

    @Test
    void whenTechnicalDescriptionValueIs255_thenInvalidRequest() throws Exception {
        assertOk(declarationDto -> declarationDto.getTechnicalDescription()
                .forEach(technicalDescriptionDto -> technicalDescriptionDto.setValue(randomAlphabetic(255))));
    }

    @Test
    void whenTechnicalDescriptionValueLargerThan255_thenInvalidRequest() throws Exception {
        String errorDescription = "Declaration technical description translation must have size not larger than 255 characters";
        assertInvalidRequest(declarationDto -> declarationDto.getTechnicalDescription()
                .forEach(technicalDescriptionDto -> technicalDescriptionDto.setValue(randomAlphabetic(256))), errorDescription);
    }

    @Test
    void whenTechnicalDescriptionLocaleNull_thenInvalidRequest() throws Exception {
        String errorDescription = "Declaration technical description must have locale";
        assertInvalidRequest(declarationDto -> declarationDto.getTechnicalDescription()
                .forEach(technicalDescriptionDto -> technicalDescriptionDto.setLocale(null)), errorDescription);
    }

    // consentMaxDurationSeconds

    @Test
    void whenConsentMaxDurationSecondsNull_thenInvalidRequest() throws Exception {
        String errorDescription = "Consent max duration seconds cannot be empty or Null";
        assertInvalidRequest(declarationDto -> declarationDto.setConsentMaxDurationSeconds(null), errorDescription);
    }

    @Test
    void whenConsentMaxDurationSecondsNegative_thenInvalidRequest() throws Exception {
        String errorDescription = "Consent max duration seconds cannot be negative";
        assertInvalidRequest(declarationDto -> declarationDto.setConsentMaxDurationSeconds(-1), errorDescription);
    }


    // validUntil

    @Test
    void whenValidUntilPresent_thenInvalidRequest() throws Exception {
        String errorDescription = "Valid until must be in future";
        assertInvalidRequest(declarationDto -> declarationDto.setValidUntil(LocalDateTime.now()), errorDescription);
    }

    @Test
    void whenValidUntilInPast_thenInvalidRequest() throws Exception {
        String errorDescription = "Valid until must be in future";
        assertInvalidRequest(declarationDto ->
                declarationDto.setValidUntil(LocalDateTime.now().minusMinutes(1)), errorDescription);
    }

    // maxCacheSeconds

    @Test
    void whenMaxCacheSecondsNegative_thenInvalidRequest() throws Exception {
        String errorDescription = "Cache seconds cannot be negative";
        assertInvalidRequest(declarationDto -> declarationDto.setMaxCacheSeconds(-1), errorDescription);
    }

    @Test
    void whenDuplicateDeclaration_thenDuplicateDeclaration() throws Exception {
        String errorDescription = "There already is a declaration by this party with such id";
        DeclarationDto declarationDto = declarationGenerator.randomDeclarationDto();
        assertOk(addDeclaration(declarationDto));
        assertErrorMessage(addDeclaration(declarationDto), "duplicate_declaration", errorDescription);
    }

    @Test
    void whenAuthenticationFail_thenInvalidRequest() throws Exception {
        String errorDescription = "Authenticated identity of the party do not match";
        HttpHeaders headers = new HttpHeaders();
        headers.add(Headers.X_AUTHENTICATED_USER_ID_HEADER, randomAlphabetic(15));
        assertInvalidRequest(addDeclaration(declarationGenerator.randomDeclarationDto(), headers), errorDescription);
    }

    @Test
    void givenLocaleNotInList_whenAddDeclaration_thenOk() throws Exception {
        DeclarationDto declarationDto = declarationGenerator.randomDeclarationDto();

        declarationDto.getName().add(declarationGenerator.randomNameDto(Locale.FRANCE));
        declarationDto.getDescription().add(declarationGenerator.randomDescriptionDto(Locale.FRANCE));
        declarationDto.getTechnicalDescription().add(declarationGenerator.randomTechnicalDescriptionDto(Locale.FRANCE));

        assertOk(addDeclaration(declarationDto));
    }

    @Test
    void givenLocaleInList_whenAddDeclarationWithoutGivenLocaleForNames_thenInvalidRequest() throws Exception {
        String errorDescription = "Not all translations are provided for declaration name. Missed locales: [it_IT]";
        DeclarationDto declarationDto = declarationGenerator.randomDeclarationDto();

        declarationDto.getDescription().add(declarationGenerator.randomDescriptionDto(Locale.ITALY));
        declarationDto.getTechnicalDescription().add(declarationGenerator.randomTechnicalDescriptionDto(Locale.ITALY));

        ServiceProviderLocaleRule serviceProviderLocaleRule = new ServiceProviderLocaleRule();
        serviceProviderLocaleRule.setServiceProviderId(declarationDto.getServiceProviderId());
        serviceProviderLocaleRule.setLocale(Locale.ITALY);

        doReturn(Set.of(serviceProviderLocaleRule))
                .when(serviceProviderLocaleRuleRepository)
                .findAllByServiceProviderId(declarationDto.getServiceProviderId());

        assertInvalidRequest(addDeclaration(declarationDto), errorDescription);
    }

    @Test
    void givenLocaleInList_whenAddDeclarationWithoutGivenLocaleForDescriptions_thenInvalidRequest() throws Exception {
        String errorDescription = "Not all translations are provided for declaration description. Missed locales: [it_IT]";
        DeclarationDto declarationDto = declarationGenerator.randomDeclarationDto();

        declarationDto.getName().add(declarationGenerator.randomNameDto(Locale.ITALY));
        declarationDto.getTechnicalDescription().add(declarationGenerator.randomTechnicalDescriptionDto(Locale.ITALY));

        ServiceProviderLocaleRule serviceProviderLocaleRule = new ServiceProviderLocaleRule();
        serviceProviderLocaleRule.setServiceProviderId(declarationDto.getServiceProviderId());
        serviceProviderLocaleRule.setLocale(Locale.ITALY);

        doReturn(Set.of(serviceProviderLocaleRule))
                .when(serviceProviderLocaleRuleRepository)
                .findAllByServiceProviderId(declarationDto.getServiceProviderId());

        assertInvalidRequest(addDeclaration(declarationDto), errorDescription);
    }

    @Test
    void whenServiceProviderIdContainsNotAsciiCharacters_thenInvalidRequest() throws Exception {
        String errorDescription = "Service provider id characters must be in ASCII range starting from 33 till 126";
        for (int i = 0; i < 33; i++) {
            DeclarationDto declarationDto = declarationGenerator.randomDeclarationDto();
            declarationDto.setServiceProviderId(String.valueOf((char) i));
            assertInvalidRequest(badRequest(addDeclaration(declarationDto)), errorDescription);
        }

        for (int i = 127; i < 256; i++) {
            DeclarationDto declarationDto = declarationGenerator.randomDeclarationDto();
            declarationDto.setServiceProviderId(String.valueOf((char) i));
            assertInvalidRequest(badRequest(addDeclaration(declarationDto)), errorDescription);
        }
    }

    @Test
    void whenServiceDeclarationIdContainsNotAsciiCharacters_thenInvalidRequest() throws Exception {
        String errorDescription = "Service declaration id characters must be in ASCII range starting from 33 till 126";
        for (int i = 0; i < 33; i++) {
            DeclarationDto declarationDto = declarationGenerator.randomDeclarationDto();
            declarationDto.setServiceDeclarationId(String.valueOf((char) i));
            assertInvalidRequest(badRequest(addDeclaration(declarationDto)), errorDescription);
        }

        for (int i = 127; i < 256; i++) {
            DeclarationDto declarationDto = declarationGenerator.randomDeclarationDto();
            declarationDto.setServiceDeclarationId(String.valueOf((char) i));
            assertInvalidRequest(badRequest(addDeclaration(declarationDto)), errorDescription);
        }
    }

    @Test
    void givenDuplicateTranslationsForName_whenAddDeclaration_thenInvalidRequest() throws Exception {
        String errorDescription = "Service declaration cannot have duplicate translation for name";

        DeclarationDto declarationDto = declarationGenerator.randomDeclarationDto();

        declarationDto.getName()
                .stream()
                .findAny()
                .map(TranslatableStringDto::getLocale)
                .map(declarationGenerator::randomNameDto)
                .ifPresent(declarationDto.getName()::add);

        assertInvalidRequest(badRequest(addDeclaration(declarationDto)), errorDescription);
    }


    @Test
    void givenDuplicateTranslationsForDescription_whenAddDeclaration_thenInvalidRequest() throws Exception {
        String errorDescription = "Service declaration cannot have duplicate translation for description";

        DeclarationDto declarationDto = declarationGenerator.randomDeclarationDto();

        declarationDto.getDescription()
                .stream()
                .findAny()
                .map(TranslatableStringDto::getLocale)
                .map(declarationGenerator::randomDescriptionDto)
                .ifPresent(declarationDto.getDescription()::add);

        assertInvalidRequest(badRequest(addDeclaration(declarationDto)), errorDescription);
    }

    @Test
    void givenDuplicateTranslationsForTechnicalDescription_whenAddDeclaration_thenInvalidRequest() throws Exception {
        String errorDescription = "Service declaration cannot have duplicate translation for technical description";

        DeclarationDto declarationDto = declarationGenerator.randomDeclarationDto();

        declarationDto.getTechnicalDescription()
                .stream()
                .findAny()
                .map(TranslatableStringDto::getLocale)
                .map(declarationGenerator::randomTechnicalDescriptionDto)
                .ifPresent(declarationDto.getTechnicalDescription()::add);

        assertInvalidRequest(badRequest(addDeclaration(declarationDto)), errorDescription);
    }

    private void assertOk(Consumer<DeclarationDto> declarationDtoConsumer) throws Exception {
        DeclarationDto declarationDto = declarationGenerator.randomDeclarationDto();
        declarationDtoConsumer.accept(declarationDto);
        assertOk(addDeclaration(declarationDto));
    }

    private void assertOk(ResultActions resultActions) throws Exception {
        ok(resultActions).andExpect(jsonPath("$.code", Matchers.is("OK")));
    }

    private void assertInvalidRequest(Consumer<DeclarationDto> declarationDtoConsumer, String errorDescription) throws Exception {
        DeclarationDto declarationDto = declarationGenerator.randomDeclarationDto();
        HttpHeaders headers = new HttpHeaders();
        headers.add(Headers.X_AUTHENTICATED_USER_ID_HEADER, declarationDto.getServiceProviderId());
        declarationDtoConsumer.accept(declarationDto);
        assertInvalidRequest(addDeclaration(declarationDto, headers), errorDescription);
    }

    private void assertInvalidRequest(ResultActions resultActions, String errorDescription) throws Exception {
        assertErrorMessage(badRequest(resultActions), "invalid_request", errorDescription);
    }

    private void assertErrorMessage(ResultActions resultActions, String errorCode, String errorDescription) throws Exception {
        resultActions
                .andExpect(jsonPath("$.code", Matchers.is(errorCode)))
                .andExpect(jsonPath("$.message", Matchers.is(errorDescription))).andDo(print());
    }

    private ResultActions ok(ResultActions resultActions) throws Exception {
        return resultActions.andExpect(status().isOk());
    }

    private ResultActions badRequest(ResultActions resultActions) throws Exception {
        return resultActions.andExpect(status().isBadRequest());
    }

    private ResultActions addDeclaration(DeclarationDto declarationDto) throws Exception {
        return mvc.perform(postJson("/declarations", declarationDto).header("X-Authenticated-User-Id",
                declarationDto.getServiceProviderId()));
    }

    private ResultActions addDeclaration(DeclarationDto declarationDto, HttpHeaders headers) throws Exception {
        return mvc.perform(postJson("/declarations", declarationDto).headers(headers));
    }

    private MockHttpServletRequestBuilder postJson(String uri, Object request) throws Exception {
        return json(MockMvcRequestBuilders.post(uri), request);
    }

    private MockHttpServletRequestBuilder json(MockHttpServletRequestBuilder builder, Object request) throws Exception {
        return builder.content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON);
    }

}