package de.innovationhub.prox.companyprofileservice.application.controller.company.quarter;

import de.innovationhub.prox.companyprofileservice.application.exception.ApiError;
import de.innovationhub.prox.companyprofileservice.domain.company.quarter.Quarter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Quarters API", description = "APIs for quarters")
@RequestMapping("quarters")
public interface QuarterController {

  @Operation(summary = "Get all quarters")
  @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
  ResponseEntity<CollectionModel<EntityModel<Quarter>>> getAllQuarters();

  @ApiResponse(
      responseCode = "400",
      description = "Invalid UUID",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "404",
      description = "No quarter with the given ID found",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(responseCode = "200", description = "OK")
  @Operation(summary = "Get quarter")
  @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
  ResponseEntity<EntityModel<Quarter>> getQuarter(
      @PathVariable("id") @Parameter(description = "UUID of quarter") UUID id);

  @ApiResponse(
      responseCode = "400",
      description = "Invalid UUID",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "404",
      description = "No quarter with the given ID found",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(responseCode = "200", description = "OK")
  @Operation(summary = "Save quarter")
  @PostMapping(produces = MediaTypes.HAL_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<EntityModel<Quarter>> saveQuarter(@RequestBody Quarter quarter);

  @ApiResponse(
      responseCode = "400",
      description = "Invalid UUID",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "404",
      description = "No quarter with the given ID found",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(responseCode = "200", description = "OK")
  @Operation(summary = "Update quarter")
  @PutMapping(
      value = "/{id}",
      produces = MediaTypes.HAL_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<EntityModel<Quarter>> updateQuarter(
      @PathVariable("id") @Parameter(description = "UUID of quarter") UUID id,
      @RequestBody Quarter quarter);

  @ApiResponse(
      responseCode = "400",
      description = "Invalid UUID",
      content =
      @Content(
          mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "404",
      description = "No quarter with the given ID found",
      content =
      @Content(
          mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(responseCode = "200", description = "OK")
  @Operation(summary = "Delete quarter")
  @DeleteMapping(
      value = "/{id}")
  ResponseEntity<?> deleteQuarter(
      @PathVariable("id") @Parameter(description = "UUID of quarter") UUID id);
}
