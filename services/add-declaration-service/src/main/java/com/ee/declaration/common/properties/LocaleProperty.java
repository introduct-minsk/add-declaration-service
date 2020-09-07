package com.ee.declaration.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Configuration
@ConfigurationProperties(prefix = "default-locale")
public class LocaleProperty {

    @Valid
    @NotEmpty
    private Set<Local> locales;

    @Data
    public static class Local {
        @NotBlank
        private String language;

        @NotBlank
        private String country;
    }

}
