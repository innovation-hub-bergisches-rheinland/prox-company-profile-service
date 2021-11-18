package de.innovationhub.prox.companyprofileservice.domain.company.language;


import java.util.Collection;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends CrudRepository<Language, UUID> {
  Iterable<Language> findAllByTypeIn(Collection<Type> types);
}
