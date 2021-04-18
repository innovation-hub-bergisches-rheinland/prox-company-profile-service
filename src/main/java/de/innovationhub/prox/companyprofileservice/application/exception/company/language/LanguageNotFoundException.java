package de.innovationhub.prox.companyprofileservice.application.exception.company.language;

import de.innovationhub.prox.companyprofileservice.application.exception.core.CustomEntityNotFoundException;

public class LanguageNotFoundException extends CustomEntityNotFoundException {
  public LanguageNotFoundException() {
    super("Language not found");
  }
}
