package de.innovationhub.prox.companyprofileservice.application.validation.language;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.validation.Validator;
import lombok.Getter;
import lombok.Value;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AllValidLanguageIsoIdentifiersValidatorTest {

  @Autowired
  private Validator validator;

  @Value
  class Foo {
    @AllValidLanguageIsoIdentifiers
    private Set<String> languages;
  }

  @Test
  void isValid() {
    var result = validator.validate(new Foo(Set.of("de", "en", "it", "es")));
    assertThat(result).isEmpty();

    result = validator.validate(new Foo(Set.of("De", "EN", "IT", "eS")));
    assertThat(result).isEmpty();

    result = validator.validate(new Foo(Collections.emptySet()));
    assertThat(result).isEmpty();

    result = validator.validate(new Foo(Set.of("Deaa", "ENGe", "IT", "eS")));
    assertThat(result).isNotEmpty();
  }
}