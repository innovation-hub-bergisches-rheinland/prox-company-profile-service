package de.innovationhub.prox.companyprofileservice.application.service.company.language;


import de.innovationhub.prox.companyprofileservice.application.exception.core.CustomEntityNotFoundException;
import de.innovationhub.prox.companyprofileservice.application.service.core.CrudService;
import de.innovationhub.prox.companyprofileservice.domain.company.language.Language;
import de.innovationhub.prox.companyprofileservice.domain.company.language.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

public interface LanguageService extends CrudService<Language, UUID> {
  Iterable<Language> getAllLanguages(Type... type);

  default Iterable<Language> getLanguagesWithIds(Iterable<UUID> uuids) {
    var language = new HashSet<Language>();
    for (UUID uuid : uuids) {
      this.getById(uuid)
          .map(language::add)
          .orElseThrow(
              () ->
                  new CustomEntityNotFoundException(
                      "Language with id " + uuid.toString() + " not found"));
    }
    return Collections.unmodifiableSet(language);
  }
}
