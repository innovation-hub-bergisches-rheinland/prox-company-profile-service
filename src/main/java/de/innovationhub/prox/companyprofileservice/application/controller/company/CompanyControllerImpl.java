package de.innovationhub.prox.companyprofileservice.application.controller.company;

import de.innovationhub.prox.companyprofileservice.application.exception.core.CustomEntityNotFoundException;
import de.innovationhub.prox.companyprofileservice.application.hateoas.CompanyRepresentationModelAssembler;
import de.innovationhub.prox.companyprofileservice.application.hateoas.LanguageRepresentationModelAssembler;
import de.innovationhub.prox.companyprofileservice.application.service.company.CompanyService;
import de.innovationhub.prox.companyprofileservice.domain.company.Company;
import de.innovationhub.prox.companyprofileservice.domain.company.language.Language;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @Override
  public ResponseEntity<CollectionModel<EntityModel<Company>>> getAllCompanies() {
    var collectionModel =
        companyRepresentationModelAssembler.toCollectionModel(companyService.getAll());
    return ResponseEntity.ok(collectionModel);
  }

  @Override
  public ResponseEntity<EntityModel<Company>> getCompany(UUID id) {
    var company = companyService.getById(id).orElseThrow(() -> new CustomEntityNotFoundException("Company with id " + id.toString() + " not found"));
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
      UUID id, UUID[] languageIds) {

    var uuids = Arrays.stream(languageIds).collect(Collectors.toSet());

    var company = companyService.setCompanyLanguages(id, uuids);
    return ResponseEntity.ok(
        languageRepresentationModelAssembler.toCollectionModel(company.getLanguages()));
  }

  @Override
  public ResponseEntity<EntityModel<Company>> saveCompany(@Valid Company company) {
    var savedCompany = this.companyService.save(company);
    var entityModel = this.companyRepresentationModelAssembler.toModel(savedCompany);
    return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
  }

  @Override
  public ResponseEntity<EntityModel<Company>> updateCompany(UUID id, @Valid Company professor) {
    if (!professor.getId().equals(id)) {
      throw new RuntimeException();
    }

    var savedCompany = this.companyService.update(id, professor);

    return ResponseEntity.ok(this.companyRepresentationModelAssembler.toModel(savedCompany));
  }
}
