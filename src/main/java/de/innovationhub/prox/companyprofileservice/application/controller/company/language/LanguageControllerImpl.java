package de.innovationhub.prox.companyprofileservice.application.controller.company.language;

import de.innovationhub.prox.companyprofileservice.application.exception.core.CustomEntityNotFoundException;
import de.innovationhub.prox.companyprofileservice.application.hateoas.LanguageRepresentationModelAssembler;
import de.innovationhub.prox.companyprofileservice.application.service.company.language.LanguageService;
import de.innovationhub.prox.companyprofileservice.domain.company.language.Language;
import de.innovationhub.prox.companyprofileservice.domain.company.language.Type;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LanguageControllerImpl implements LanguageController {

  private final LanguageService languageService;
  private final LanguageRepresentationModelAssembler languageRepresentationModelAssembler;

  @Autowired
  public LanguageControllerImpl(
      LanguageService languageService,
      LanguageRepresentationModelAssembler languageRepresentationModelAssembler) {
    this.languageService = languageService;
    this.languageRepresentationModelAssembler = languageRepresentationModelAssembler;
  }

  @Override
  public ResponseEntity<CollectionModel<EntityModel<Language>>> getAllLanguages(Type[] types) {
    var collectionModel =
        languageRepresentationModelAssembler.toCollectionModel(
            languageService.getAllLanguages(types));
    return ResponseEntity.ok(collectionModel);
  }

  @Override
  public ResponseEntity<EntityModel<Language>> getLanguage(UUID id) {
    return this.languageService
        .getById(id)
        .map(languageRepresentationModelAssembler::toModel)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new CustomEntityNotFoundException("Language with id " + id.toString() + " not found"));  }
}
