package com.ee.declaration.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Add Declaration teenuse vastus.")
public class Response {

    @Getter
    @Schema(description = "Add Declaration teenuse vastuse kood. Võimalikud väärtused: " +
            "'OK', 'invalid_request', 'duplicate_declaration'")
    private final String code;

    @Getter
    @Schema(description = "Vea kirjeldus. Väli ei ole tagastatav õnnestunud päringute puhul, " +
            "mille HTTP kood on HTTP/200.")
    private String message;

}
