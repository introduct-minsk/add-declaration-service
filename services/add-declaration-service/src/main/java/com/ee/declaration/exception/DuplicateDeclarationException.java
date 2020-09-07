package com.ee.declaration.exception;

import com.ee.declaration.web.response.annotation.AddDeclarationResponseStatus;
import org.springframework.http.HttpStatus;

@AddDeclarationResponseStatus(
        httpCode = HttpStatus.BAD_REQUEST,
        code = "Exception.DuplicateDeclaration.code",
        description = "Exception.DuplicateDeclaration.message"
)
public class DuplicateDeclarationException extends AddDeclarationException {
}
