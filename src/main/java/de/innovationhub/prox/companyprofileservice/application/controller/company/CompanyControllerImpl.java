package de.innovationhub.prox.companyprofileservice.application.controller.company;

import de.innovationhub.prox.companyprofileservice.application.exception.ApiError;
import de.innovationhub.prox.companyprofileservice.application.exception.company.CompanyNotFoundException;
import de.innovationhub.prox.companyprofileservice.application.exception.core.CustomEntityNotFoundException;
import de.innovationhub.prox.companyprofileservice.application.exception.language.LanguageNotFoundException;
import de.innovationhub.prox.companyprofileservice.application.hateoas.CompanyRepresentationModelAssembler;
import de.innovationhub.prox.companyprofileservice.application.hateoas.LanguageRepresentationModelAssembler;
import de.innovationhub.prox.companyprofileservice.application.service.company.CompanyService;
import de.innovationhub.prox.companyprofileservice.application.service.language.LanguageService;
import de.innovationhub.prox.companyprofileservice.domain.company.Company;
import de.innovationhub.prox.companyprofileservice.domain.language.Language;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.apache.commons.codec.language.bm.Lang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyControllerImpl implements CompanyController {

  private final CompanyService companyService;
  private final CompanyRepresentationModelAssembler companyRepresentationModelAssembler;
  private final LanguageRepresentationModelAssembler languageRepresentationModelAssembler;
  private final Logger logger = LoggerFactory.getLogger(CompanyControllerImpl.class);

  @Autowired
  public CompanyControllerImpl(
      CompanyService companyService,
      CompanyRepresentationModelAssembler companyRepresentationModelAssembler,
      LanguageRepresentationModelAssembler languageRepresentationModelAssembler) {
    this.companyService = companyService;
    this.companyRepresentationModelAssembler = companyRepresentationModelAssembler;
    this.languageRepresentationModelAssembler = languageRepresentationModelAssembler;
  }

  @ExceptionHandler({CustomEntityNotFoundException.class})
  public ResponseEntity<ApiError> entityNotFoundExceptionHandler(CustomEntityNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ApiError(HttpStatus.NOT_FOUND.value(), e.getType(), e.getMessage()));
  }

  @ExceptionHandler({IllegalArgumentException.class})
  public ResponseEntity<ApiError> entityNotFoundExceptionHandler(IllegalArgumentException e) {
    logger.error(e.getMessage(), e);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ApiError(HttpStatus.BAD_REQUEST.value(), "Illegal Argument", e.getMessage()));
  }

  @Override
  public ResponseEntity<CollectionModel<EntityModel<Company>>> getAllCompanies() {
    var collectionModel =
        companyRepresentationModelAssembler.toCollectionModel(companyService.getAllCompanies());
    return ResponseEntity.ok(collectionModel);
  }

  @Override
  public ResponseEntity<EntityModel<Company>> getCompany(UUID id) {
    var company = companyService.getCompanyById(id).orElseThrow(CompanyNotFoundException::new);
    return ResponseEntity.ok(companyRepresentationModelAssembler.toModel(company));
  }

  @Override
  public ResponseEntity<CollectionModel<EntityModel<Language>>> getCompanyLanguages(UUID id) {
    return ResponseEntity.ok(
        languageRepresentationModelAssembler.toCollectionModel(
            companyService.getCompanyLanguages(id)));
  }

  @Override
  public ResponseEntity<CollectionModel<EntityModel<Language>>> putCompanyLanguages(
      UUID id, String[] languageIds) {

    var uuids = Arrays.stream(languageIds).map(UUID::fromString).collect(Collectors.toSet());

    var company = companyService.setCompanyLanguages(id, uuids);
    return ResponseEntity.ok(
        languageRepresentationModelAssembler.toCollectionModel(company.getLanguages()));
  }

  @Override
  public ResponseEntity<EntityModel<Company>> saveCompany(@Valid Company company) {
    var savedCompany = this.companyService.saveCompany(company);
    var entityModel =
        this.companyRepresentationModelAssembler.toModel(savedCompany);
    return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
  }

  @Override
  public ResponseEntity<EntityModel<Company>> updateCompany(UUID id, @Valid Company professor) {
    if (!professor.getId().equals(id)) {
      throw new RuntimeException();
    }

    var savedCompany = this.companyService.updateCompany(professor);

    return ResponseEntity.ok(this.companyRepresentationModelAssembler.toModel(savedCompany));
  }
}
