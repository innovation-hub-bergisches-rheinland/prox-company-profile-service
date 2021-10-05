package de.innovationhub.prox.companyprofileservice.application.service;

import de.innovationhub.prox.companyprofileservice.domain.dtos.language.LanguageDto;
import de.innovationhub.prox.companyprofileservice.domain.entities.language.Language;
import de.innovationhub.prox.companyprofileservice.domain.repositories.language.LanguageRepository;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LanguageService {

  private final LanguageRepository languageRepository;

  @Autowired
  public LanguageService(
      LanguageRepository languageRepository) {
    this.languageRepository = languageRepository;
  }

  public Set<Language> findAllByIsoIdentifier(Set<String> identifiers) {
    return languageRepository.findAllByIsoIdentifier2InIgnoreCase(identifiers);
  }

  public Set<LanguageDto> getAllLanguages() {
    return StreamSupport.stream(this.languageRepository.findAll().spliterator(), false)
        .map(this::mapToDto)
        .collect(Collectors.toSet());
  }

  // TODO: Use automatic mapping, something like ModelMapper or MapStruct, but ensure safety
  private LanguageDto mapToDto(Language language) {
    return new LanguageDto(
        language.getIsoIdentifier2(),
        language.getGermanName(),
        language.getEnglishName(),
        language.getIso3166Mapping()
    );
  }
}
