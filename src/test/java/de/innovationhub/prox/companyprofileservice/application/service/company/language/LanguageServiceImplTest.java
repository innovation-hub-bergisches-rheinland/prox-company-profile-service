package de.innovationhub.prox.companyprofileservice.application.service.company.language;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.innovationhub.prox.companyprofileservice.domain.company.language.Language;
import de.innovationhub.prox.companyprofileservice.domain.company.language.LanguageRepository;
import de.innovationhub.prox.companyprofileservice.domain.company.language.Type;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

@Import({LanguageServiceImpl.class})
@SpringBootTest
class LanguageServiceImplTest {

  @MockBean LanguageRepository languageRepository;

  @Autowired LanguageService languageService;

  @BeforeEach
  void setUp() {}

  Language sampleLanguage = new Language("de", "German", "Deutsch", Type.LIVING, "de");
  Language sampleLanguage2 = new Language("en", "English", "Englisch", Type.LIVING, "en");
  Iterable<Language> sampleLanguages = Set.of(sampleLanguage, sampleLanguage2);

  @DisplayName("Should return all languages")
  @Test
  void shouldReturnAllLanguages() {
    when(languageRepository.findAll()).thenReturn(sampleLanguages);

    assertThat(languageService.getAll()).containsExactlyInAnyOrderElementsOf(sampleLanguages);

    verify(languageRepository).findAll();
  }

  @DisplayName("Should return language")
  @Test
  void shouldReturnLanguage() {
    when(languageRepository.findById(eq(sampleLanguage.getId())))
        .thenReturn(Optional.of(sampleLanguage));

    assertThat(languageService.getById(sampleLanguage.getId())).get().isEqualTo(sampleLanguage);

    verify(languageRepository).findById(eq(sampleLanguage.getId()));
  }

  @DisplayName("Should return empty optional")
  @Test
  void shouldReturnEmptyOptional() {
    when(languageRepository.findById(any())).thenReturn(Optional.empty());

    assertThat(languageService.getById(sampleLanguage.getId())).isEmpty();

    verify(languageRepository).findById(eq(sampleLanguage.getId()));
  }

  @DisplayName("Should save language")
  @Test
  void shouldSaveLanguage() {
    when(languageRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    assertThat(languageService.save(sampleLanguage)).isEqualTo(sampleLanguage);

    verify(languageRepository).save(eq(sampleLanguage));
  }

  @DisplayName("Should update language")
  @Test
  void shouldUpdateLanguage() {
    when(languageRepository.findById(eq(sampleLanguage.getId())))
        .thenReturn(Optional.of(sampleLanguage));
    when(languageRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    var updated = languageService.update(sampleLanguage.getId(), sampleLanguage2);

    // Verify that id is not updated
    assertThat(updated.getId()).isEqualTo(sampleLanguage.getId());

    // TODO: Test that the other properties were set

    verify(languageRepository).findById(eq(sampleLanguage.getId()));
    verify(languageRepository).save(any());
  }

  @DisplayName("Should delete language")
  @Test
  void shouldDeleteLanguage() {
    languageRepository.deleteById(sampleLanguage.getId());

    verify(languageRepository).deleteById(eq(sampleLanguage.getId()));
  }

  @DisplayName("Should return all languages")
  @Test
  void testGetAllLanguages1() {
    languageService.getAllLanguages(null);

    verify(languageRepository).findAll();
  }

  @DisplayName("Should return all languages")
  @Test
  void testGetAllLanguages2() {
    languageService.getAllLanguages(new Type[] {});

    verify(languageRepository).findAll();
  }

  @DisplayName("Should return all languages of type")
  @Test
  void testGetAllLanguages3() {
    Type[] types = Type.values();
    languageService.getAllLanguages(types);

    verify(languageRepository).findAllByTypeIn(argThat(t -> t.containsAll(Arrays.asList(types))));
  }
}
