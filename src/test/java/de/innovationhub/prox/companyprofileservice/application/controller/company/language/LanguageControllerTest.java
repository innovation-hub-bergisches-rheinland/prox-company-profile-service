package de.innovationhub.prox.companyprofileservice.application.controller.company.language;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.innovationhub.prox.companyprofileservice.application.config.SecurityConfig;
import de.innovationhub.prox.companyprofileservice.application.config.WebConfig;
import de.innovationhub.prox.companyprofileservice.application.hateoas.LanguageRepresentationModelAssembler;
import de.innovationhub.prox.companyprofileservice.application.security.UserIsOwnerOfCompanyPermissionEvaluator;
import de.innovationhub.prox.companyprofileservice.application.service.company.language.LanguageService;
import de.innovationhub.prox.companyprofileservice.domain.company.language.Language;
import de.innovationhub.prox.companyprofileservice.domain.company.language.LanguageSampleData;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(controllers = LanguageController.class)
@Import({
  LanguageRepresentationModelAssembler.class,
  LanguageRepresentationModelAssembler.class,
  WebConfig.class,
  SecurityConfig.class
})
@RunWith(SpringRunner.class)
class LanguageControllerTest {

  @MockBean private LanguageService languageService;

  @Autowired private WebApplicationContext context;

  @MockBean private UserIsOwnerOfCompanyPermissionEvaluator userIsOwnerOfCompanyPermissionEvaluator;

  private Language sampleLanguage;
  private Iterable<Language> sampleLanguages;

  @BeforeEach
  void setup() {
    var languageSampleData = new LanguageSampleData();
    this.sampleLanguage = languageSampleData.getSAMPLE_LANGUAGE_1();
    this.sampleLanguages = languageSampleData.getSAMPLE_LANGUAGES();

    // unnecessary as KeycloakConfig.class is not in ApplicationContext. Leave it in for reference
    when(userIsOwnerOfCompanyPermissionEvaluator.hasPermission(any(), any(), any()))
        .thenReturn(true);
    when(userIsOwnerOfCompanyPermissionEvaluator.hasPermission(any(), any(), any(), any()))
        .thenReturn(true);
  }

  @Test
  void testGetAllLanguages() throws Exception {
    when(languageService.getAllLanguages(any())).thenReturn(this.sampleLanguages);

    given()
        .webAppContextSetup(context)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
        .when()
        .get("/languages")
        .then()
        .log()
        .all()
        .status(HttpStatus.OK)
        .body("_embedded.languageList", hasSize(3))
        .body(
            "_embedded.languageList[0]._links.self.href",
            response ->
                equalTo(
                    "http://localhost/languages/" + response.path("_embedded.languageList[0].id")))
        .body("_links.self.href", equalTo("http://localhost/languages{?types}"))
        .body("_links.self.templated", equalTo(true));

    verify(languageService).getAllLanguages(any());
  }

  @Test
  void testGetLanguageById() {
    when(languageService.getById(eq(sampleLanguage.getId())))
        .thenReturn(Optional.of(sampleLanguage));

    given()
        .webAppContextSetup(context)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
        .when()
        .get("/languages/{id}", sampleLanguage.getId())
        .then()
        .status(HttpStatus.OK)
        .body(
            "_links.self.href",
            response -> equalTo("http://localhost/languages/" + response.path("id")));

    verify(languageService).getById(eq(sampleLanguage.getId()));
  }

  @Test
  void testGetLanguageByIdWithInvalidId() {
    when(languageService.getById(any(UUID.class))).thenReturn(Optional.empty());

    given()
        .webAppContextSetup(context)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
        .when()
        .get("/languages/{id}", UUID.randomUUID())
        .then()
        .status(HttpStatus.NOT_FOUND)
        .body("status", response -> equalTo(response.statusCode()))
        .body("error", is(not(emptyString())))
        .body("message", is(not(emptyString())));

    verify(languageService).getById(any(UUID.class));
  }
}
