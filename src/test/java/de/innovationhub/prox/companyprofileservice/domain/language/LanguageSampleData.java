package de.innovationhub.prox.companyprofileservice.domain.language;

import java.util.Set;
import lombok.Getter;

@Getter
public class LanguageSampleData {
  private final Language SAMPLE_LANGUAGE_1;
  private final Language SAMPLE_LANGUAGE_2;
  private final Language SAMPLE_LANGUAGE_3;
  private final Set<Language> SAMPLE_LANGUAGES;

  public LanguageSampleData() {
    this. SAMPLE_LANGUAGE_1 = new Language("de", "German", "Deutsch", Type.LIVING);
    this. SAMPLE_LANGUAGE_2 = new Language("en", "English", "Englisch", Type.LIVING);
    this. SAMPLE_LANGUAGE_3 = new Language("ru", "Russian", "Russisch", Type.LIVING);
    this. SAMPLE_LANGUAGES = Set.of(SAMPLE_LANGUAGE_1, SAMPLE_LANGUAGE_2, SAMPLE_LANGUAGE_3);
  }
}
