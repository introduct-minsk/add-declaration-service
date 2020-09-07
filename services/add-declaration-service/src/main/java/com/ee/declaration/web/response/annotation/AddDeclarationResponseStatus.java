package com.ee.declaration.web.response.annotation;

import org.springframework.http.HttpStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AddDeclarationResponseStatus {

    HttpStatus httpCode() default HttpStatus.INTERNAL_SERVER_ERROR;

    String code();

    String description();
}
