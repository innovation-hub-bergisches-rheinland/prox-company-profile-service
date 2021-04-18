package de.innovationhub.prox.companyprofileservice.application.service.language;

import de.innovationhub.prox.companyprofileservice.domain.language.Language;
import de.innovationhub.prox.companyprofileservice.domain.language.LanguageRepository;
import de.innovationhub.prox.companyprofileservice.domain.language.Type;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LanguageServiceImpl implements LanguageService {

  private final LanguageRepository languageRepository;

  @Autowired
  public LanguageServiceImpl(LanguageRepository languageRepository) {
    this.languageRepository = languageRepository;
  }

  @Override
  public Optional<Language> getLanguage(UUID uuid) {
    return languageRepository.findById(uuid);
  }

  @Override
  public Iterable<Language> getAllLanguages() {
    return languageRepository.findAll();
  }

  @Override
  public Iterable<Language> getAllLanguages(Type... types) {
    if (types == null || types.length == 0) {
      return this.getAllLanguages();
    }
    return languageRepository.findAllByTypeIn(Arrays.asList(types));
  }
}
