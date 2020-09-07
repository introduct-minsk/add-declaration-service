package com.ee.declaration.exception;

import com.ee.declaration.web.response.annotation.AddDeclarationResponseStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Locale;
import java.util.Set;

@AddDeclarationResponseStatus(
        httpCode = HttpStatus.BAD_REQUEST,
        code = "Exception.InvalidLocale.code",
        description = "Exception.InvalidLocale.message"
)
@RequiredArgsConstructor
public class InvalidLocaleException extends AddDeclarationException {

    @Getter
    private final String field;
    @Getter
    private final Set<Locale> locales;

}
