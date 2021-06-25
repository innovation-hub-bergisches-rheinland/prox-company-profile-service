package de.innovationhub.prox.companyprofileservice.application.service.company.language;

import de.innovationhub.prox.companyprofileservice.application.exception.core.CustomEntityNotFoundException;
import de.innovationhub.prox.companyprofileservice.domain.company.language.Language;
import de.innovationhub.prox.companyprofileservice.domain.company.language.LanguageRepository;
import de.innovationhub.prox.companyprofileservice.domain.company.language.Type;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LanguageServiceImpl implements LanguageService {

  private final LanguageRepository languageRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public LanguageServiceImpl(LanguageRepository languageRepository) {
    this.languageRepository = languageRepository;
    this.modelMapper = new ModelMapper();
  }

  @Override
  public Optional<Language> getById(UUID uuid) {
    return languageRepository.findById(uuid);
  }

  @Override
  public Language save(Language language) {
    return languageRepository.save(language);
  }

  @Override
  public Language update(UUID id, Language language) {
    return this.getById(id)
        .map(
            l -> {
              modelMapper.map(language, l);
              return l;
            })
        .map(languageRepository::save)
        .orElseThrow(
            () ->
                new CustomEntityNotFoundException(
                    "Language with id " + id.toString() + " not found"));
  }

  @Override
  public void deleteById(UUID uuid) {
    languageRepository.deleteById(uuid);
  }

  @Override
  public Iterable<Language> getAll() {
    return languageRepository.findAll();
  }

  @Override
  public Iterable<Language> getAllLanguages(Type... types) {
    if (types == null || types.length == 0) {
      return this.getAll();
    }
    return languageRepository.findAllByTypeIn(Arrays.asList(types));
  }
}
