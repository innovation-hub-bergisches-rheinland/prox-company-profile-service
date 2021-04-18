package de.innovationhub.prox.companyprofileservice.application.service.language;

import de.innovationhub.prox.companyprofileservice.application.exception.language.LanguageNotFoundException;
import de.innovationhub.prox.companyprofileservice.domain.language.Language;
import de.innovationhub.prox.companyprofileservice.domain.language.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

public interface LanguageService {
  Optional<Language> getLanguage(UUID uuid);

  Iterable<Language> getAllLanguages();

  Iterable<Language> getAllLanguages(Type... type);

  default Iterable<Language> getLanguagesWithIds(Iterable<UUID> uuids) {
    var language = new HashSet<Language>();
    for (UUID uuid : uuids) {
      this.getLanguage(uuid).ifPresentOrElse(language::add, LanguageNotFoundException::new);
    }
    return Collections.unmodifiableSet(language);
  }
}
