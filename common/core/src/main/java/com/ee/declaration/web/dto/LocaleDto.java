package com.ee.declaration.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@Schema(description = "Lokaali, teisisõnu tõlke keele kood - määrab mis keeles on tõlge, mille juurde ta määratud."
)
public class LocaleDto {

    @NotEmpty
    @Size(min = 2, max = 2)
    @Schema(required = true, description = "ISO 639-1 formaadis keele kood, määramas väärtuse lokaali. " +
            "(inimkeeles: määramas tõlke keelt)")
    private String language;

    @NotEmpty
    @Size(min = 2, max = 2)
    @Schema(required = true, description = "ISO 3166-1 alpha 2 formaadis riigi kood, määramas väärtuse lokaali. " +
            "(inimkeeles: määramas tõlke keelt)")
    private String country;

}
