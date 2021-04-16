package de.innovationhub.prox.companyprofileservice.application.controller.company;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.innovationhub.prox.companyprofileservice.application.config.WebConfig;
import de.innovationhub.prox.companyprofileservice.application.hateoas.CompanyRepresentationModelAssembler;
import de.innovationhub.prox.companyprofileservice.application.hateoas.LanguageRepresentationModelAssembler;
import de.innovationhub.prox.companyprofileservice.application.service.company.CompanyService;
import de.innovationhub.prox.companyprofileservice.application.service.language.LanguageService;
import de.innovationhub.prox.companyprofileservice.domain.company.Branch;
import de.innovationhub.prox.companyprofileservice.domain.company.Company;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyInformation;
import de.innovationhub.prox.companyprofileservice.domain.company.Quarter;
import de.innovationhub.prox.companyprofileservice.domain.language.Language;
import de.innovationhub.prox.companyprofileservice.domain.language.Type;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(controllers = CompanyController.class)
@Import({
    CompanyRepresentationModelAssembler.class,
    LanguageRepresentationModelAssembler.class,
    WebConfig.class
})
@RunWith(SpringRunner.class)
class CompanyControllerTest {

  @MockBean
  private CompanyService companyService;

  @MockBean
  private LanguageService languageService;

  @Autowired
  private WebApplicationContext context;

  private Company sampleCompany;

  @BeforeEach
  private void setup() {
    this.sampleCompany = new Company(new CompanyInformation("Null Ltd.", "2021-04-17", "about null", "https://null.org", "Null"), new Quarter("Null"),
        Collections.emptyList(),
        Collections.singletonList(new Language("de", "German", "Deutsch",
            de.innovationhub.prox.companyprofileservice.domain.language.Type.LIVING)), Arrays.asList(new Branch("null1"), new Branch("null2")));
  }

  @Test
  void testGetAllCompanies() {
    when(companyService.getAllCompanies()).thenReturn(Collections.singletonList(this.sampleCompany));

    given()
        .webAppContextSetup(context)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
    .when()
        .get("/companies")
    .then()
        .status(HttpStatus.OK)
        .body("_embedded.companyList", hasSize(1))
        .body("_embedded.companyList[0]._links.self.href", response -> equalTo("http://localhost/companies/" + response.path("_embedded.companyList[0].id")))
        .body("_links.self.href", equalTo("http://localhost/companies"));

    verify(companyService).getAllCompanies();
  }

  @Test
  void testGetCompanyById() {
    when(companyService.getCompanyById(eq(sampleCompany.getId()))).thenReturn(Optional.of(sampleCompany));

    given()
        .webAppContextSetup(context)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
    .when()
        .get("/companies/{id}", sampleCompany.getId())
    .then()
        .status(HttpStatus.OK)
        .body("_links.self.href", response -> equalTo("http://localhost/companies/" + response.path("id")));

    verify(companyService).getCompanyById(eq(sampleCompany.getId()));
  }

  @Test
  void testGetCompanyLanguages() {
    when(companyService.getCompanyLanguages(eq(sampleCompany.getId()))).thenReturn(Collections.singletonList(new Language("de", "German", "Deutsch", Type.LIVING)));

    given()
        .webAppContextSetup(context)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
    .when()
        .get("/companies/{id}/languages", sampleCompany.getId())
    .then()
        .log().all()
        .status(HttpStatus.OK)
        .body("_embedded.languageList", hasSize(1))
        .body("_embedded.languageList[0]._links.self.href", response -> equalTo("http://localhost/languages/" + response.path("_embedded.languageList[0].id")));
        //.body("_links.self.href", response -> equalTo("http://localhost/companies/" + response.path("id") + "/languages"))
        //TODO: Check self link

    verify(companyService).getCompanyLanguages(eq(sampleCompany.getId()));
  }

  @Test
  void testSetCompanyLanguages() {
    var language1 = new Language("de", "German", "Deutsch", Type.LIVING);
    var language2 = new Language("en", "English", "Englisch", Type.LIVING);
    when(languageService.getLanguage(eq(language1.getId()))).thenReturn(Optional.of(language1));
    when(languageService.getLanguage(eq(language2.getId()))).thenReturn(Optional.of(language2));
    when(companyService.setCompanyLanguages(eq(this.sampleCompany.getId()), anyList())).thenAnswer(invocation -> {
      this.sampleCompany.setLanguages(invocation.getArgument(1));
      return this.sampleCompany;
    });

    String[] uuids = new String[] {
        language1.getId().toString(),
        language2.getId().toString()
    };

    given()
        .webAppContextSetup(context)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .body(uuids)
        .when()
        .put("/companies/{id}/languages", sampleCompany.getId())
        .then()
        .log().all()
        .status(HttpStatus.OK)
        .body("_embedded.languageList", hasSize(2))
        .body("_embedded.languageList[0]._links.self.href", response -> equalTo("http://localhost/languages/" + response.path("_embedded.languageList[0].id")))
        .body("_embedded.languageList[0].id", equalTo(uuids[0]))
        .body("_embedded.languageList[1]._links.self.href", response -> equalTo("http://localhost/languages/" + response.path("_embedded.languageList[1].id")))
        .body("_embedded.languageList[1].id", equalTo(uuids[1]));

    verify(companyService).setCompanyLanguages(eq(this.sampleCompany.getId()), anyList());
  }

  @Test
  void testSetCompanyLanguagesInvalid() {
    when(languageService.getLanguage(any(UUID.class))).thenReturn(Optional.empty());
    when(companyService.setCompanyLanguages(eq(this.sampleCompany.getId()), anyList())).thenAnswer(invocation -> {
      this.sampleCompany.setLanguages(invocation.getArgument(1));
      return this.sampleCompany;
    });

    String[] uuids = new String[] {
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString()
    };

    given()
        .webAppContextSetup(context)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .body(uuids)
        .when()
        .put("/companies/{id}/languages", sampleCompany.getId())
        .then()
        .log().all()
        .status(HttpStatus.NOT_FOUND)
        .body("status", response -> equalTo(response.statusCode()))
        .body("error", is(not(emptyString())))
        .body("message", is(not(emptyString())));

    verify(languageService).getLanguage(any(UUID.class));
    verify(companyService, times(0)).setCompanyLanguages(any(UUID.class), anyList());
  }

  @Test
  void testSetCompanyLanguagesInvalidUUID() {
    String[] uuids = new String[] {
        "abcdefghijklmnopqrstuvwxyz12345567890"
    };

    given()
        .webAppContextSetup(context)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .body(uuids)
        .when()
        .put("/companies/{id}/languages", sampleCompany.getId())
        .then()
        .log().all()
        .status(HttpStatus.BAD_REQUEST)
        .body("status", response -> equalTo(response.statusCode()))
        .body("error", is(not(emptyString())))
        .body("message", is(not(emptyString())));
  }

  @Test
  void testGetCompanyByIdWithInvalidId() {
    when(companyService.getCompanyById(any(UUID.class))).thenReturn(Optional.empty());

    given()
        .webAppContextSetup(context)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
    .when()
        .get("/companies/{id}", UUID.randomUUID())
    .then()
        .status(HttpStatus.NOT_FOUND)
        .body("status", response -> equalTo(response.statusCode()))
        .body("error", is(not(emptyString())))
        .body("message", is(not(emptyString())));

    verify(companyService).getCompanyById(any(UUID.class));
  }

  @Test
  void testSaveCompany() {
    when(companyService.saveCompany(sampleCompany)).thenReturn(sampleCompany);

    given()
        .webAppContextSetup(context)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .body(this.sampleCompany)
        .log().all()
    .when()
        .post("/companies")
    .then()
        .log().all()
        .status(HttpStatus.CREATED)
        .body("_links.self.href", response -> equalTo("http://localhost/companies/" + response.path("id")));

    verify(companyService).saveCompany(eq(sampleCompany));
  }

  @Test
  void testUpdateCompany() {
    when(companyService.updateCompany(sampleCompany)).thenReturn(sampleCompany);

    given()
        .webAppContextSetup(context)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .body(sampleCompany)
    .when()
        .put("/companies/{id}", sampleCompany.getId())
    .then()
        .status(HttpStatus.OK)
        .body("_links.self.href", response -> equalTo("http://localhost/companies/" + response.path("id")));

    verify(companyService).updateCompany(eq(sampleCompany));
  }
}