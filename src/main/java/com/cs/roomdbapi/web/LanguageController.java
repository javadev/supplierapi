package com.cs.roomdbapi.web;

import com.cs.roomdbapi.dto.Language;
import com.cs.roomdbapi.manager.LanguageManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Tag(
        name = "Languages",
        description = "API endpoints to access language names and codes. <br/>" +
                "Each language has codes in different ISO formats: <br/>" +
                "- ISO 639-1 two-letter, <br/>" +
                "- ISO 639-2/T three-letter, <br/>" +
                "- ISO 639-2/B three-letter. <br/>" +
                "- ISO 639-3 three-letter. <br/>"
)
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) " +
        "or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/languages", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class LanguageController {

    private final LanguageManager languageManager;

    @Operation(
            summary = "Get list of all languages.",
            description = "All fields of the Language entity will be included in result."
    )
    @GetMapping
    public ResponseEntity<List<Language>> getAllLanguages() {
        List<Language> languages = languageManager.getLanguages();

        return new ResponseEntity<>(languages, HttpStatus.OK);
    }

    @Operation(
            summary = "Get language data by id."
    )
    @GetMapping({"/{id}"})
    public ResponseEntity<Language> getLanguage(
            @PathVariable
            @Parameter(description = "RoomDB internal language Id. Required.")
            @Min(1)
                    Integer id
    ) {

        return new ResponseEntity<>(languageManager.getLanguageById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Get language data by ISO 639-1 two-letter code."
    )
    @GetMapping({"/two-latter-code/{code}"})
    public ResponseEntity<Language> getLanguageByCode(
            @PathVariable
            @Parameter(description = "Two letters language code (ISO 639-1 two-letter). Required.")
            @Size(min = 2, max = 2)
                    String code
    ) {

        return new ResponseEntity<>(languageManager.getLanguageByCode(code.toLowerCase()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN)")
    @Operation(
            summary = "Add language."
    )
    @PostMapping
    public ResponseEntity<Language> addLanguage(
            @Valid @RequestBody Language language
    ) {
        Language newLanguage = languageManager.addLanguage(language);

        return new ResponseEntity<>(newLanguage, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN)")
    @Operation(
            summary = "Update language."
    )
    @PutMapping("/{id}")
    public ResponseEntity<Language> updateLanguage(
            @PathVariable("id")
            @Parameter(description = "RoomDB internal language Id. Required.")
            @Min(1) Integer id,
            @Valid @RequestBody Language language
    ) {
        Language updatedLang = languageManager.updateLanguage(id, language);

        return ResponseEntity.ok(updatedLang);
    }

}
