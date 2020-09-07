package com.ee.declaration.web.dto;

import com.ee.declaration.web.validator.UniqueLocale;
import com.ee.declaration.web.validator.ValidInACSIIRange;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Schema(description = "Add Declaration teenuse päring.")
public class DeclarationDto {

    @NotEmpty
    @Size(max = 100)
    @ValidInACSIIRange(min = 33, max = 126)
    @Schema(required = true, description = "Deklarandi identifikaator. Sama ID peab olema määratud HTTP päises " +
            "‘X-Authenticated-User-Id’. Väärtus, spetsifikatsiooni järgi lepitakse kokku deklarandi ja " +
            "teenuse operaatori (oletame, et RIA) vahel.")
    private String serviceProviderId;

    @NotEmpty
    @Size(max = 40)
    @ValidInACSIIRange(min = 33, max = 126)
    @Schema(required = true, description = "Deklaratsiooni identifikaator. Määratud deklarandi poolt. " +
            "Unikaalne deklarandi deklaratsioonide hulgas.")
    private String serviceDeclarationId;

    @Valid
    @NotEmpty
    @UniqueLocale
    @Schema(required = true, description = "Deklareeritava teenuse nimi. Võib sisaldada tõlget mitmes keeles.")
    private List<NameDto> name;

    @Valid
    @NotEmpty
    @UniqueLocale
    @Schema(required = true, description = "Teenuse deklaratsiooni kirjeldus. Võib sisaldada tõlget mitmes keeles.")
    private List<DescriptionDto> description;

    @Valid
    @NotEmpty
    @UniqueLocale
    @Schema(required = true, description = "Teenuse deklaratsiooni tehniline kirjeldus. Võib sisaldada tõlget " +
            "mitmes keeles.")
    private List<TechnicalDescriptionDto> technicalDescription;

    @Min(value = 0)
    @NotNull
    @Schema(required = true, description = "Deklaratsiooni avaldamise nõusoleku kehtivusaeg, sekundites.")
    private Integer consentMaxDurationSeconds;

    @Schema(description = "Märge, tähistamas kas deklaratsiooni avaldamine vajab digitaalselt allkirjastatud nõusoleku " +
            "andmesubjektilt.")
    private Boolean needSignature;

    @Future
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Deklaratsiooni kehtivusaeg, mille vältel deklarant nõustub teenust osutama vastavalt " +
            "deklaratsioonile. Ajatempli eeldetakse Eesti ajatsoonis. (Europe/Tallinn)",
            pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime validUntil;

    @Min(value = 0)
    @Schema(description = "Teenuse validatsiooni vastuse puhverdamise maksimaalne aeg. Sekundites.")
    private Integer maxCacheSeconds;

}