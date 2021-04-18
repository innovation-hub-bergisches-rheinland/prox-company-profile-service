package de.innovationhub.prox.companyprofileservice.application.controller.company;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.innovationhub.prox.companyprofileservice.application.config.WebConfig;
import de.innovationhub.prox.companyprofileservice.application.exception.language.LanguageNotFoundException;
import de.innovationhub.prox.companyprofileservice.application.hateoas.CompanyRepresentationModelAssembler;
import de.innovationhub.prox.companyprofileservice.application.hateoas.LanguageRepresentationModelAssembler;
import de.innovationhub.prox.companyprofileservice.application.service.company.CompanyService;
import de.innovationhub.prox.companyprofileservice.domain.company.Company;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanySampleData;
import de.innovationhub.prox.companyprofileservice.domain.language.Language;
import de.innovationhub.prox.companyprofileservice.domain.language.Type;
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

//TODO: refactoring
@WebMvcTest(controllers = CompanyController.class)
@Import({
  CompanyRepresentationModelAssembler.class,
  LanguageRepresentationModelAssembler.class,
  WebConfig.class
})
@RunWith(SpringRunner.class)
class CompanyControllerTest {

  @MockBean private CompanyService companyService;

  @Autowired private WebApplicationContext context;

  private Company sampleCompany;

  @BeforeEach
  public void setup() {
    var companySampleData = new CompanySampleData();
    this.sampleCompany = companySampleData.getSAMPLE_COMPANY_1();
  }

  @Test
  void testGetAllCompanies() {
    when(companyService.getAllCompanies())
        .thenReturn(Collections.singletonList(this.sampleCompany));

    given()
        .webAppContextSetup(context)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
        .when()
        .get("/companies")
        .then()
        .status(HttpStatus.OK)
        .body("_embedded.companyList", hasSize(1))
        .body(
            "_embedded.companyList[0]._links.self.href",
            response ->
                equalTo(
                    "http://localhost/companies/" + response.path("_embedded.companyList[0].id")))
        .body("_links.self.href", equalTo("http://localhost/companies"));

    verify(companyService).getAllCompanies();
  }

  @Test
  void testGetCompanyById() {
    when(companyService.getCompanyById(eq(sampleCompany.getId())))
        .thenReturn(Optional.of(sampleCompany));

    given()
        .webAppContextSetup(context)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
        .when()
        .get("/companies/{id}", sampleCompany.getId())
        .then()
        .status(HttpStatus.OK)
        .body(
            "_links.self.href",
            response -> equalTo("http://localhost/companies/" + response.path("id")));

    verify(companyService).getCompanyById(eq(sampleCompany.getId()));
  }

  @Test
  void testGetCompanyLanguages() {
    when(companyService.getCompanyLanguages(eq(sampleCompany.getId())))
        .thenReturn(
            Collections.singletonList(new Language("de", "German", "Deutsch", Type.LIVING)));

    given()
        .webAppContextSetup(context)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
        .when()
        .get("/companies/{id}/languages", sampleCompany.getId())
        .then()
        .log()
        .all()
        .status(HttpStatus.OK)
        .body("_embedded.languageList", hasSize(1))
        .body(
            "_embedded.languageList[0]._links.self.href",
            response ->
                equalTo(
                    "http://localhost/languages/" + response.path("_embedded.languageList[0].id")));
    // .body("_links.self.href", response -> equalTo("http://localhost/companies/" +
    // response.path("id") + "/languages"))
    // TODO: Check self link

    verify(companyService).getCompanyLanguages(eq(sampleCompany.getId()));
  }

  @Test
  void testSetCompanyLanguages() {
    when(companyService.setCompanyLanguages(eq(this.sampleCompany.getId()), anySet())).thenReturn(this.sampleCompany);

    String[] uuids = new String[] {UUID.randomUUID().toString(), UUID.randomUUID().toString()};

    given()
        .webAppContextSetup(context)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .body(uuids)
        .when()
        .put("/companies/{id}/languages", sampleCompany.getId())
        .then()
        .log()
        .all()
        .status(HttpStatus.OK);

    verify(companyService).setCompanyLanguages(eq(this.sampleCompany.getId()), anySet());
  }

  @Test
  void testSetCompanyLanguagesInvalid() {
    when(companyService.setCompanyLanguages(eq(this.sampleCompany.getId()), anySet())).thenThrow(new LanguageNotFoundException());

    String[] uuids = new String[] {UUID.randomUUID().toString(), UUID.randomUUID().toString()};

    given()
        .webAppContextSetup(context)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .body(uuids)
        .when()
        .put("/companies/{id}/languages", sampleCompany.getId())
        .then()
        .log()
        .all()
        .status(HttpStatus.NOT_FOUND)
        .body("status", response -> equalTo(response.statusCode()))
        .body("error", is(not(emptyString())))
        .body("message", is(not(emptyString())));

    verify(companyService).setCompanyLanguages(any(UUID.class), anySet());
  }

  @Test
  void testSetCompanyLanguagesInvalidUUID() {
    String[] uuids = new String[] {"abcdefghijklmnopqrstuvwxyz12345567890"};

    given()
        .webAppContextSetup(context)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .body(uuids)
        .when()
        .put("/companies/{id}/languages", sampleCompany.getId())
        .then()
        .log()
        .all()
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
    when(companyService.saveCompany(any(Company.class))).thenReturn(sampleCompany);

    given()
        .webAppContextSetup(context)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .body(this.sampleCompany)
        .log()
        .all()
        .when()
        .post("/companies")
        .then()
        .log()
        .all()
        .status(HttpStatus.CREATED)
        .body(
            "_links.self.href",
            response -> equalTo("http://localhost/companies/" + response.path("id")));

    verify(companyService).saveCompany(any(Company.class));
  }

  @Test
  void testUpdateCompany() {
    when(companyService.updateCompany(any(Company.class))).thenReturn(sampleCompany);

    given()
        .webAppContextSetup(context)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .body(sampleCompany)
        .when()
        .put("/companies/{id}", sampleCompany.getId())
        .then()
        .status(HttpStatus.OK)
        .body(
            "_links.self.href",
            response -> equalTo("http://localhost/companies/" + response.path("id")));

    verify(companyService).updateCompany(any(Company.class));
  }
}
