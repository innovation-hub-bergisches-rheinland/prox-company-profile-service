package de.innovationhub.prox.companyprofileservice.application.validation.language;

import de.innovationhub.prox.companyprofileservice.domain.entities.language.Language;
import de.innovationhub.prox.companyprofileservice.domain.repositories.language.LanguageRepository;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AllValidLanguageIsoIdentifiersValidator
    implements ConstraintValidator<AllValidLanguageIsoIdentifiers, Iterable<String>> {

  private final Set<Language> languages;

  @Autowired
  public AllValidLanguageIsoIdentifiersValidator(
      LanguageRepository languageRepository) {
    this.languages = StreamSupport.stream(languageRepository.findAll().spliterator(), false)
        .collect(
            Collectors.toSet());
  }

  @Override
  public boolean isValid(Iterable<String> value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }
    return StreamSupport.stream(value.spliterator(), false)
        .allMatch(v -> languages.stream().anyMatch(l -> l.getIsoIdentifier2().equalsIgnoreCase(v)));
  }
}
