package de.innovationhub.prox.companyprofileservice.application.controller.company.quarter;

import de.innovationhub.prox.companyprofileservice.application.exception.ApiError;
import de.innovationhub.prox.companyprofileservice.application.exception.core.CustomEntityNotFoundException;
import de.innovationhub.prox.companyprofileservice.application.hateoas.QuarterRepresentationModelAssembler;
import de.innovationhub.prox.companyprofileservice.application.service.company.quarter.QuarterService;
import de.innovationhub.prox.companyprofileservice.domain.company.quarter.Quarter;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuarterControllerImpl implements QuarterController {

  private final QuarterService quarterService;
  private final QuarterRepresentationModelAssembler quarterRepresentationModelAssembler;

  @Autowired
  public QuarterControllerImpl(
      QuarterService quarterService,
      QuarterRepresentationModelAssembler quarterRepresentationModelAssembler) {
    this.quarterService = quarterService;
    this.quarterRepresentationModelAssembler = quarterRepresentationModelAssembler;
  }

  @ExceptionHandler({CustomEntityNotFoundException.class})
  public ResponseEntity<ApiError> entityNotFoundExceptionHandler(CustomEntityNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ApiError(HttpStatus.NOT_FOUND.value(), e.getType(), e.getMessage()));
  }

  @Override
  public ResponseEntity<CollectionModel<EntityModel<Quarter>>> getAllQuarters() {
    return ResponseEntity.ok(
        this.quarterRepresentationModelAssembler.toCollectionModel(this.quarterService.getAll()));
  }

  @Override
  public ResponseEntity<EntityModel<Quarter>> getQuarter(UUID id) {
    return this.quarterService
        .getById(id)
        .map(quarterRepresentationModelAssembler::toModel)
        .map(ResponseEntity::ok)
        .orElseThrow(
            () -> new CustomEntityNotFoundException("Quarter with id " + id + " not found"));
  }

  @Override
  public ResponseEntity<EntityModel<Quarter>> saveQuarter(@Valid Quarter quarter) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(quarterRepresentationModelAssembler.toModel(this.quarterService.save(quarter)));
  }

  @Override
  public ResponseEntity<EntityModel<Quarter>> updateQuarter(UUID id, Quarter quarter) {
    return ResponseEntity.ok(
        quarterRepresentationModelAssembler.toModel(this.quarterService.update(id, quarter)));
  }
}
