package com.ee.declaration.exception;

import com.ee.declaration.web.response.annotation.AddDeclarationResponseStatus;
import org.springframework.http.HttpStatus;

@AddDeclarationResponseStatus(
        httpCode = HttpStatus.BAD_REQUEST,
        code = "Exception.AuthenticationFailure.code",
        description = "Exception.AuthenticationFailure.message"
)
public class AuthenticationException extends AddDeclarationException {

}
