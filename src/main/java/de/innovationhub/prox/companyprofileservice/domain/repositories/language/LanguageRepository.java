package de.innovationhub.prox.companyprofileservice.domain.repositories.language;

import de.innovationhub.prox.companyprofileservice.domain.entities.language.Language;
import de.innovationhub.prox.companyprofileservice.domain.entities.language.Type;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends CrudRepository<Language, UUID> {
  Iterable<Language> findAllByTypeIn(Collection<Type> types);
  Optional<Language> findByIsoIdentifier2(String identifier);
  Set<Language> findAllByIsoIdentifier2InIgnoreCase(Collection<String> identifiers);
}
