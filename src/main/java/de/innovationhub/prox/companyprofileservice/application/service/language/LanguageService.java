package de.innovationhub.prox.companyprofileservice.application.service.language;

import de.innovationhub.prox.companyprofileservice.domain.language.Language;
import de.innovationhub.prox.companyprofileservice.domain.language.Type;
import java.util.Optional;
import java.util.UUID;

public interface LanguageService {
  Optional<Language> getLanguage(UUID uuid);
  Iterable<Language> getAllLanguages();
  Iterable<Language> getAllLanguages(Type... type);
}
