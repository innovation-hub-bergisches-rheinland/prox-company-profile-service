package de.innovationhub.prox.companyprofileservice.domain.company.language;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

@Getter
public class LanguageSampleData {
  private final Language SAMPLE_LANGUAGE_1;
  private final Language SAMPLE_LANGUAGE_2;
  private final Language SAMPLE_LANGUAGE_3;
  private final Set<Language> SAMPLE_LANGUAGES;

  public LanguageSampleData() {
    this.SAMPLE_LANGUAGE_1 = new Language("de", "German", "Deutsch", Type.LIVING, "de");
    this.SAMPLE_LANGUAGE_2 = new Language("en", "English", "Englisch", Type.LIVING, "gb");
    this.SAMPLE_LANGUAGE_3 = new Language("ru", "Russian", "Russisch", Type.LIVING, "ru");
    this.SAMPLE_LANGUAGES =
        new HashSet<>(Arrays.asList(SAMPLE_LANGUAGE_1, SAMPLE_LANGUAGE_2, SAMPLE_LANGUAGE_3));
  }
}
