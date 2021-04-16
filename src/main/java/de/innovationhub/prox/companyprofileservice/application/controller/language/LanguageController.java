package de.innovationhub.prox.companyprofileservice.application.controller.language;

import de.innovationhub.prox.companyprofileservice.application.exception.ApiError;
import de.innovationhub.prox.companyprofileservice.domain.company.Company;
import de.innovationhub.prox.companyprofileservice.domain.language.Language;
import de.innovationhub.prox.companyprofileservice.domain.language.Type;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Language API", description = "APIs for languages")
@RequestMapping("languages")
public interface LanguageController {

  @Operation(summary = "Get all langauges")
  @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
  ResponseEntity<CollectionModel<EntityModel<Language>>> getAllLanguages(@RequestParam(required = false, name = "types") Type[] types);

  @ApiResponse(
      responseCode = "400",
      description = "Invalid UUID",
      content =
      @Content(
          mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "404",
      description = "No language with the given ID found",
      content =
      @Content(
          mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(responseCode = "200", description = "OK")
  @Operation(summary = "Get language")
  @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
  ResponseEntity<EntityModel<Language>> getLanguage(
      @PathVariable("id") @Parameter(description = "UUID of language") UUID id);

}
