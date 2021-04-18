package de.innovationhub.prox.companyprofileservice.application.service.language;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

import de.innovationhub.prox.companyprofileservice.application.service.company.CompanyServiceImpl;
import de.innovationhub.prox.companyprofileservice.domain.language.LanguageRepository;
import de.innovationhub.prox.companyprofileservice.domain.language.Type;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

@Import({
    LanguageServiceImpl.class
})
@SpringBootTest
class LanguageServiceImplTest {

  @MockBean
  LanguageRepository languageRepository;

  @Autowired
  LanguageService languageService;

  @BeforeEach
  void setUp() {
  }

  @Test
  void getLanguage() {
    //CRUD not needed
  }

  @Test
  void getAllLanguages() {
    //CRUD not needed
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
    languageService.getAllLanguages(new Type[]{});

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