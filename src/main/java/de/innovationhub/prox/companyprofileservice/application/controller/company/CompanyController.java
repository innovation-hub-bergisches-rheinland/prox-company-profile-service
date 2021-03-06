package de.innovationhub.prox.companyprofileservice.application.controller.company;


import de.innovationhub.prox.companyprofileservice.application.exception.ApiError;
import de.innovationhub.prox.companyprofileservice.domain.company.Company;
import de.innovationhub.prox.companyprofileservice.domain.company.language.Language;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Company API", description = "APIs for companies")
@RequestMapping("companies")
public interface CompanyController {

  @Operation(summary = "Get all companies")
  @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
  ResponseEntity<CollectionModel<EntityModel<Company>>> getAllCompanies();

  @ApiResponse(
      responseCode = "404",
      description = "No company with the given ID found",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(responseCode = "200", description = "OK")
  @Operation(
      summary = "Get owned company of authenticated user",
      security = @SecurityRequirement(name = "Bearer"))
  @GetMapping(value = "/me", produces = MediaTypes.HAL_JSON_VALUE)
  ResponseEntity<EntityModel<Company>> getMyCompany();

  @ApiResponse(
      responseCode = "400",
      description = "Invalid UUID",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "404",
      description = "No company with the given ID found",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(responseCode = "200", description = "OK")
  @Operation(summary = "Get company")
  @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
  ResponseEntity<EntityModel<Company>> getCompany(
      @PathVariable("id") @Parameter(description = "UUID of company") UUID id);

  @ApiResponse(
      responseCode = "400",
      description = "Invalid UUID",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "404",
      description = "No company with the given ID found",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(responseCode = "200", description = "OK")
  @Operation(summary = "Get company languages")
  @GetMapping(value = "/{id}/languages", produces = MediaTypes.HAL_JSON_VALUE)
  ResponseEntity<CollectionModel<EntityModel<Language>>> getCompanyLanguages(
      @PathVariable("id") @Parameter(description = "UUID of company") UUID id);

  @ApiResponse(
      responseCode = "400",
      description = "Invalid UUID",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "404",
      description = "No company with the given ID found",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(responseCode = "200", description = "OK")
  @Operation(summary = "Set company languages", security = @SecurityRequirement(name = "Bearer"))
  @PutMapping(
      value = "/{id}/languages",
      produces = MediaTypes.HAL_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<CollectionModel<EntityModel<Language>>> putCompanyLanguages(
      @PathVariable("id") @Parameter(description = "UUID of company") UUID id,
      @RequestBody @Parameter(description = "Language UUIDs") UUID[] ids);

  @ApiResponse(responseCode = "201", description = "Created")
  @ApiResponse(
      responseCode = "401",
      description = "Unauthorized",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "403",
      description = "Forbidden",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class)))
  @Operation(summary = "save company", security = @SecurityRequirement(name = "Bearer"))
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaTypes.HAL_JSON_VALUE)
  ResponseEntity<EntityModel<Company>> saveCompany(@RequestBody Company company);

  @Operation(summary = "update company", security = @SecurityRequirement(name = "Bearer"))
  @ApiResponse(responseCode = "200", description = "Updated")
  @ApiResponse(responseCode = "201", description = "Created if company not exists")
  @ApiResponse(
      responseCode = "400",
      description = "Invalid UUID",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "401",
      description = "Unauthorized",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "403",
      description = "Forbidden",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "404",
      description = "No company with the given ID found",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class)))
  @PutMapping(
      value = "/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaTypes.HAL_JSON_VALUE)
  ResponseEntity<EntityModel<Company>> updateCompany(
      @PathVariable UUID id, @RequestBody Company professor);

  @ApiResponse(
      responseCode = "400",
      description = "Invalid UUID",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "404",
      description = "No company with the given ID found",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(responseCode = "200", description = "OK")
  @Operation(summary = "Get company")
  @GetMapping(value = "/search/findCompanyByCreatorId", produces = MediaTypes.HAL_JSON_VALUE)
  ResponseEntity<EntityModel<Company>> findCompanyByCreatorId(@RequestParam UUID creatorId);
}
