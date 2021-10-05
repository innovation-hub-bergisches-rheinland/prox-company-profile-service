package de.innovationhub.prox.companyprofileservice.application.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.innovationhub.prox.companyprofileservice.application.service.LanguageService;
import de.innovationhub.prox.companyprofileservice.domain.dtos.language.LanguageDto;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
class LanguageControllerTest {

  @Autowired
  WebApplicationContext context;

  @MockBean
  LanguageService languageService;

  @BeforeEach
  public void setup() {
    RestAssuredMockMvc.webAppContextSetup(context);
  }

  @Test
  void getAllLanguages() {
    var langDE = new LanguageDto(
        "de",
        "Deutsch",
        "German",
        "DE"
    );
    when(languageService.getAllLanguages()).thenReturn(Set.of(langDE));

    given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .get("/languages")
        .then()
        .status(HttpStatus.OK)
        .body("[0].iso_identifier", equalTo("de"))
        .body("[0].german_name", equalTo("Deutsch"))
        .body("[0].english_name", equalTo("German"))
        .body("[0].iso3166_mapping", equalTo("DE"));

    verify(languageService).getAllLanguages();
  }
}