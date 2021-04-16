package de.innovationhub.prox.companyprofileservice.application.controller.language;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.innovationhub.prox.companyprofileservice.application.config.WebConfig;
import de.innovationhub.prox.companyprofileservice.application.controller.company.CompanyController;
import de.innovationhub.prox.companyprofileservice.application.hateoas.CompanyRepresentationModelAssembler;
import de.innovationhub.prox.companyprofileservice.application.hateoas.LanguageRepresentationModelAssembler;
import de.innovationhub.prox.companyprofileservice.application.service.company.CompanyService;
import de.innovationhub.prox.companyprofileservice.application.service.language.LanguageService;
import de.innovationhub.prox.companyprofileservice.domain.company.Branch;
import de.innovationhub.prox.companyprofileservice.domain.company.Company;
import de.innovationhub.prox.companyprofileservice.domain.company.Quarter;
import de.innovationhub.prox.companyprofileservice.domain.language.Language;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.config.RestAssuredMockMvcConfig;
import io.restassured.path.json.mapper.factory.Jackson2ObjectMapperFactory;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(controllers = LanguageController.class)
@Import({
    LanguageRepresentationModelAssembler.class,
    WebConfig.class
})
@RunWith(SpringRunner.class)
class LanguageControllerTest {

  @MockBean
  private LanguageService languageService;

  @Autowired
  private WebApplicationContext context;

  private Language sampleLanguage;
  private Iterable<Language> sampleLanguages;

  @BeforeEach
  private void setup() {
    this.sampleLanguage = new Language("de", "German", "Deutsch", de.innovationhub.prox.companyprofileservice.domain.language.Type.LIVING);

    this.sampleLanguages = Arrays.asList(this.sampleLanguage, new Language("en", "English", "German", de.innovationhub.prox.companyprofileservice.domain.language.Type.LIVING), new Language("ak", "Akan", "Akan", de.innovationhub.prox.companyprofileservice.domain.language.Type.ANCIENT));
  }

  @Test
  void testGetAllLanguages() {
    when(languageService.getAllLanguages(any())).thenReturn(this.sampleLanguages);

    given()
        .webAppContextSetup(context)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
    .when()
        .get("/languages")
    .then()
        .log().all()
        .status(HttpStatus.OK)
        .body("_embedded.languageList", hasSize(3))
        .body("_embedded.languageList[0]._links.self.href", response -> equalTo("http://localhost/languages/" + response.path("_embedded.languageList[0].id")))
        .body("_links.self.href", equalTo("http://localhost/languages{?types}"))
        .body("_links.self.templated", equalTo(true));

    verify(languageService).getAllLanguages(any());
  }

  @Test
  void testGetLanguageById() {
    when(languageService.getLanguage(eq(sampleLanguage.getId()))).thenReturn(Optional.of(sampleLanguage));


        given()
            .webAppContextSetup(context)
            .header("Accept", MediaTypes.HAL_JSON_VALUE)
        .when()
            .get("/languages/{id}", sampleLanguage.getId())
        .then()
            .status(HttpStatus.OK)
            .body("_links.self.href", response -> equalTo("http://localhost/languages/" + response.path("id")));

    verify(languageService).getLanguage(eq(sampleLanguage.getId()));
  }

  @Test
  void testGetLanguageByIdWithInvalidId() {
    when(languageService.getLanguage(any(UUID.class))).thenReturn(Optional.empty());

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

    verify(languageService).getLanguage(any(UUID.class));
  }
}