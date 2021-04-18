package de.innovationhub.prox.companyprofileservice.application.controller.company.language;

import static org.assertj.core.api.Assertions.assertThat;

import de.innovationhub.prox.companyprofileservice.domain.company.language.Language;
import de.innovationhub.prox.companyprofileservice.domain.company.language.LanguageSampleData;
import de.innovationhub.prox.companyprofileservice.domain.company.language.Type;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

@JsonTest
class LanguageJsonTest {

  @Autowired JacksonTester<Language> json;

  @Test
  void testSerialize() throws Exception {
    var language = new LanguageSampleData().getSAMPLE_LANGUAGE_1();

    JsonContent<Language> result = this.json.write(language);

    System.out.println(result.getJson());

    assertThat(result).extractingJsonPathValue("$.id").isEqualTo(language.getId().toString());
    assertThat(result)
        .extractingJsonPathValue("$.isoIdentifier2")
        .isEqualTo(language.getIsoIdentifier2());
    assertThat(result)
        .extractingJsonPathValue("$.englishName")
        .isEqualTo(language.getEnglishName());
    assertThat(result).extractingJsonPathValue("$.germanName").isEqualTo(language.getGermanName());
    assertThat(result).extractingJsonPathValue("$.type").isEqualTo(language.getType().toString());
  }

  @Test
  void testDeserialize() throws Exception {
    String jsonContent =
        "{\"id\":\"7d23b739-4eea-407c-8e53-b83edf743e7f\",\"isoIdentifier2\":\"de\",\"englishName\":\"German\",\"germanName\":\"Deutsch\",\"type\":\"LIVING\"}";

    Language language = this.json.parse(jsonContent).getObject();

    assertThat(language.getId()).isEqualTo(UUID.fromString("7d23b739-4eea-407c-8e53-b83edf743e7f"));

    assertThat(language.getIsoIdentifier2()).isEqualTo("de");
    assertThat(language.getEnglishName()).isEqualTo("German");
    assertThat(language.getGermanName()).isEqualTo("Deutsch");
    assertThat(language.getType()).isEqualTo(Type.LIVING);
  }
}
