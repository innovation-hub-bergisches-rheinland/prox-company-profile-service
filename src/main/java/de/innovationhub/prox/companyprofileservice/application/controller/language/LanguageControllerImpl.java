package de.innovationhub.prox.companyprofileservice.application.controller.language;

import de.innovationhub.prox.companyprofileservice.application.exception.ApiError;
import de.innovationhub.prox.companyprofileservice.application.exception.company.CompanyNotFoundException;
import de.innovationhub.prox.companyprofileservice.application.exception.language.LanguageNotFoundException;
import de.innovationhub.prox.companyprofileservice.application.hateoas.LanguageRepresentationModelAssembler;
import de.innovationhub.prox.companyprofileservice.application.service.language.LanguageService;
import de.innovationhub.prox.companyprofileservice.domain.language.Language;
import de.innovationhub.prox.companyprofileservice.domain.language.Type;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LanguageControllerImpl implements LanguageController {

  private final LanguageService languageService;
  private final LanguageRepresentationModelAssembler languageRepresentationModelAssembler;

  @Autowired
  public LanguageControllerImpl(LanguageService languageService, LanguageRepresentationModelAssembler languageRepresentationModelAssembler) {
    this.languageService = languageService;
    this.languageRepresentationModelAssembler = languageRepresentationModelAssembler;
  }

  @ExceptionHandler({LanguageNotFoundException.class})
  public ResponseEntity<ApiError> languageNotFoundException(LanguageNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ApiError(HttpStatus.NOT_FOUND.value(), e.getType(), e.getMessage()));
  }

  @Override
  public ResponseEntity<CollectionModel<EntityModel<Language>>> getAllLanguages(Type[] types) {
    var collectionModel = languageRepresentationModelAssembler.toCollectionModel(
        languageService.getAllLanguages(types));
    return ResponseEntity.ok(collectionModel);
  }

  @Override
  public ResponseEntity<EntityModel<Language>> getLanguage(UUID id) {
    return this.languageService.getLanguage(id).map(languageRepresentationModelAssembler::toModel).map(ResponseEntity::ok).orElseThrow(
        LanguageNotFoundException::new);
  }
}
