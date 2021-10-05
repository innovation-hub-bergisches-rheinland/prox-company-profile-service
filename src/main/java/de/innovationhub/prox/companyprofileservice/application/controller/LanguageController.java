package de.innovationhub.prox.companyprofileservice.application.controller;

import de.innovationhub.prox.companyprofileservice.application.service.LanguageService;
import de.innovationhub.prox.companyprofileservice.domain.dtos.language.LanguageDto;
import java.util.Set;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LanguageController {

  private final LanguageService languageService;

  public LanguageController(
      LanguageService languageService) {
    this.languageService = languageService;
  }

  @GetMapping(value = "/languages", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Set<LanguageDto>> getAllLanguages() {
    return ResponseEntity.ok(languageService.getAllLanguages());
  }
}
