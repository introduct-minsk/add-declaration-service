package com.ee.declaration.web.controller;

import com.ee.declaration.common.Headers;
import com.ee.declaration.exception.AuthenticationException;
import com.ee.declaration.mapper.DeclarationMapper;
import com.ee.declaration.service.DeclarationService;
import com.ee.declaration.web.dto.DeclarationDto;
import com.ee.declaration.web.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;
import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/declarations", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
@Tag(
        name = "declaration",
        description = "Add Declaration teenuse kasutus deklarandi poolt, lisamas teenuse deklaratsiooni."
)
public class DeclarationRestController {

    private final DeclarationService declarationService;
    private final DeclarationMapper declarationMapper;
    private final MessageSource messageSource;

    @PostMapping
    @Operation(
            summary = "Add declaration API",
            description = "Add declaration API kasutus, lisamas teenuse deklaratsiooni ja seeläbi saamas teenusepakkujaks."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Deklaratsioon on edukalt lisatud.",
                    content = @Content(
                            schema = @Schema(implementation = Response.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"code\": \"OK\"\n" +
                                    "}")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Deklaratsiooni ei ole lisatud.",
                    content = @Content(
                            schema = @Schema(implementation = Response.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"code\": \"invalid_request\",\n" +
                                    "  \"message\": \"Authenticated identity of the party do not match\"\n" +
                                    "}")
                    )
            )
    })
    public ResponseEntity<Response> addDeclaration(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Deklaratsiooni lisamise päring.",
                    content = @Content(schema = @Schema(implementation = DeclarationDto.class)))
            @RequestBody @Valid DeclarationDto declarationDto, HttpServletRequest request) {
        String header = request.getHeader(Headers.X_AUTHENTICATED_USER_ID_HEADER);
        log.info("Received declaration: {} with authentication header: {}", declarationDto, header);

        if (!Objects.equals(header, declarationDto.getServiceProviderId())) {
            log.info("Authentication is failed for declaration: {}", declarationDto);
            throw new AuthenticationException();
        }

        declarationService.add(declarationMapper.toDomain(declarationDto));
        return ResponseEntity.ok().body(new Response(getSuccessMessage(request.getLocale())));
    }

    private String getSuccessMessage(Locale locale) {
        return messageSource.getMessage("Response.Success", null, "OK", locale);
    }

}
