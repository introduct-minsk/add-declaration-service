package com.ee.declaration.common;

import com.ee.declaration.exception.AddDeclarationException;
import com.ee.declaration.exception.InvalidLocaleException;
import com.ee.declaration.web.response.Response;
import com.ee.declaration.web.response.annotation.AddDeclarationResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;
import java.util.regex.Pattern;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(AddDeclarationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> handleAuthenticationException(Exception ex, WebRequest request) {
        if (ex instanceof InvalidLocaleException) {
            InvalidLocaleException invalidLocaleException = (InvalidLocaleException) ex;
            return handleAddDeclarationExceptionException(ex, request, invalidLocaleException.getField(),
                    invalidLocaleException.getLocales());
        }
        return handleAddDeclarationExceptionException(ex, request);
    }

    private ResponseEntity<Response> handleAddDeclarationExceptionException(Exception ex, WebRequest request,
                                                                            Object... parameters) {
        log.error("Handled exception for request: {}", request, ex);
        Class<? extends Exception> exceptionClass = ex.getClass();
        AddDeclarationResponseStatus annotation = exceptionClass.getAnnotation(AddDeclarationResponseStatus.class);
        String code = getMessage(annotation.code(), request.getLocale());
        String message = getMessage(annotation.description(), request.getLocale(), parameters);
        log.debug("Response error code: {} message: {}", code, message);
        return ResponseEntity.status(annotation.httpCode()).body(new Response(code, message));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        log.error("Handled exception for request: {}", request, ex);
        FieldError fieldError = getFieldError(ex);
        if (fieldError != null) {
            log.debug("Error field: {}, message code: {}", fieldError.getField(), fieldError.getDefaultMessage());
            String message = getMessage(fieldError, request.getLocale());
            log.debug("Error message: {}", message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(fieldError.getDefaultMessage(), message));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    private FieldError getFieldError(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getAllErrors().stream()
                .filter(error -> error instanceof FieldError)
                .map(FieldError.class::cast)
                .findFirst()
                .orElse(null);
    }

    private String getMessage(FieldError fieldError, Locale locale) {
        Pattern pattern = Pattern.compile("[0-9]+");
        if (pattern.matcher(fieldError.getField()).find()) {
            String code = pattern.matcher(fieldError.getField())
                    .replaceAll("")
                    .replace("[", "")
                    .replace("]", "");
            String messageCode = "Exception." + code + "." + fieldError.getCode() + ".message";
            return getMessage(messageCode, locale);
        } else {
            String messageCode = "Exception." + fieldError.getField() + "." + fieldError.getCode() + ".message";
            return getMessage(messageCode, locale);
        }
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        String code = getMessage("Exception.HttpMessageNotReadableException.code", request.getLocale());
        String message = getMessage("Exception.HttpMessageNotReadableException.message", request.getLocale());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(code, message));
    }

    private String getMessage(String messageCode, Locale locale, Object... parameters) {
        return messageSource.getMessage(messageCode, parameters, messageCode, locale);
    }

}
