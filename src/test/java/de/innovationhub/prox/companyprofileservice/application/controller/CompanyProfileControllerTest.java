package de.innovationhub.prox.companyprofileservice.application.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.innovationhub.prox.companyprofileservice.application.service.CompanyService;
import de.innovationhub.prox.companyprofileservice.domain.dtos.company.CompanyDto;
import de.innovationhub.prox.companyprofileservice.domain.dtos.company.SocialMediaDto;
import de.innovationhub.prox.companyprofileservice.domain.entities.company.CompanyLogo;
import de.innovationhub.prox.companyprofileservice.domain.repositories.CompanyRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
class CompanyProfileControllerTest {

  @Autowired
  private WebApplicationContext context;

  @MockBean
  private CompanyService companyService;

  @BeforeEach
  public void setup() {
    RestAssuredMockMvc.webAppContextSetup(context);
  }

  @DisplayName("POST /orgs/{id}/profile should return 201 and profile")
  @Test
  public void testCreateCompanyProfile() {
    var companyJson = "{" +
          "\"name\": \"Musterfirma GmbH & Co. KG\"," +
          "\"founding_date\": \"2021-04-10\"," +
          "\"number_of_employees\": \"About 1\","+
          "\"homepage\": \"https://example.org\","+
          "\"contact_email\": \"ceo@example.org\","+
          "\"vita\": \"Lorem Ipsum\","+
          "\"headquarter\": \"Germany\","+
          "\"languages\": ["+
            "\"DE\","+
            "\"EN\""+
          "],"+
          "\"quarters\": ["+
            "\"Abu Dhabi\","+
            "\"England\""+
           "],"+
           "\"branches\": ["+
             "\"automotive\","+
             "\"innovation\""+
           "],"+
           "\"social\": {"+
             "\"facebook_handle\": \"musterfirmaGmbH\","+
             "\"twitter_handle\": \"musterfirmaGmbH\","+
             "\"instagram_handle\": \"musterfirmaGmbH\","+
             "\"xing_handle\": \"musterfirmaGmbH\","+
             "\"linkedIn_handle\": \"musterfirmaGmbH\""+
           "}"+
        "}";
    var orgId = UUID.randomUUID();
    when(companyService.saveCompany(eq(orgId), ArgumentMatchers.any())).thenAnswer(invocation -> invocation.getArgument(1));

    given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(companyJson)
    .when()
        .post("/orgs/{id}/profile", orgId)
    .then()
        .statusCode(201)
        .body("name", equalTo("Musterfirma GmbH & Co. KG"))
        .body("founding_date", equalTo("2021-04-10"))
        .body("number_of_employees", equalTo("About 1"))
        .body("homepage", equalTo("https://example.org"))
        .body("contact_email", equalTo("ceo@example.org"))
        .body("vita", equalTo("Lorem Ipsum"))
        .body("headquarter", equalTo("Germany"))
        .body("quarters", hasItems("Abu Dhabi", "England"))
        .body("branches", hasItems("automotive", "innovation"))
        .body("social", notNullValue())
        .body("social.facebook_handle", equalTo("musterfirmaGmbH"))
        .body("social.twitter_handle", equalTo("musterfirmaGmbH"))
        .body("social.instagram_handle", equalTo("musterfirmaGmbH"))
        .body("social.xing_handle", equalTo("musterfirmaGmbH"))
        .body("social.linkedIn_handle", equalTo("musterfirmaGmbH"))
        .body("languages", hasItems(equalToIgnoringCase("DE"), equalToIgnoringCase("EN")));

    verify(companyService).saveCompany(eq(orgId), ArgumentMatchers.any());
  }

  @DisplayName("GET /orgs/{id}/profile should return 200 and profile")
  @Test
  void getCompanyProfileTest() {
    var orgId = UUID.randomUUID();
    var company = new CompanyDto("Musterfirma GmbH & Co. KG",
        "2021-04-10",
        "About 1",
        "https://example.org",
        "ceo@example.org",
        "Lorem Ipsum",
        "Germany",
        Set.of("Abu Dhabi", "England"),
        Set.of("automotive", "innovation"),
        Set.of("DE", "EN"),
        new SocialMediaDto(
            "musterfirmaGmbH",
            "musterfirmaGmbH",
            "musterfirmaGmbH",
            "musterfirmaGmbH",
            "musterfirmaGmbH"
        ));
    when(companyService.getCompanyOfOrg(eq(orgId))).thenReturn(Optional.of(company));

    given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .get("/orgs/{id}/profile", orgId)
        .then()
        .statusCode(200)
        .body("name", equalTo("Musterfirma GmbH & Co. KG"))
        .body("founding_date", equalTo("2021-04-10"))
        .body("number_of_employees", equalTo("About 1"))
        .body("homepage", equalTo("https://example.org"))
        .body("contact_email", equalTo("ceo@example.org"))
        .body("vita", equalTo("Lorem Ipsum"))
        .body("headquarter", equalTo("Germany"))
        .body("quarters", hasItems("Abu Dhabi", "England"))
        .body("branches", hasItems("automotive", "innovation"))
        .body("social", notNullValue())
        .body("social.facebook_handle", equalTo("musterfirmaGmbH"))
        .body("social.twitter_handle", equalTo("musterfirmaGmbH"))
        .body("social.instagram_handle", equalTo("musterfirmaGmbH"))
        .body("social.xing_handle", equalTo("musterfirmaGmbH"))
        .body("social.linkedIn_handle", equalTo("musterfirmaGmbH"))
        .body("languages", hasItems(equalToIgnoringCase("DE"), equalToIgnoringCase("EN")));
  }

  @DisplayName("GET /orgs/{id}/profile should return 404")
  @Test
  void getCompanyProfileTest_NoProfile() {
    var orgId = UUID.randomUUID();
    when(companyService.getCompanyOfOrg(eq(orgId))).thenReturn(Optional.empty());

    given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .get("/orgs/{id}/profile", orgId)
        .then()
        .statusCode(404);
  }

  @DisplayName("POST /orgs/{id}/profile/logo should return 200")
  @Test
  void testSaveOrgLogo() throws Exception {
    var orgId = UUID.randomUUID();
    var file = ResourceUtils.getFile("classpath:img/wikipedia.png");
    var bytes = Files.readAllBytes(file.toPath());
    var companyLogo = new CompanyLogo(UUID.randomUUID(), 12315L, "image/png");
    when(companyService.saveOrganizationLogo(eq(orgId), ArgumentMatchers.any()))
        .thenReturn(Optional.of(companyLogo));

    given()
        .multiPart("image", file)
        .when()
        .post("/orgs/{id}/profile/logo", orgId)
        .then()
        .status(HttpStatus.OK);

    verify(companyService).saveOrganizationLogo(eq(orgId), ArgumentMatchers.any());
  }

  @DisplayName("POST /orgs/{id}/profile/logo should return 404")
  @Test
  void testSaveOrgLogo_NotFound() throws Exception {
    var orgId = UUID.randomUUID();
    var file = ResourceUtils.getFile("classpath:img/wikipedia.png");
    var bytes = Files.readAllBytes(file.toPath());
    when(companyService.saveOrganizationLogo(eq(orgId), ArgumentMatchers.any()))
        .thenReturn(Optional.empty());

    given()
        .multiPart("image", file)
        .when()
        .post("/orgs/{id}/profile/logo", orgId)
        .then()
        .status(HttpStatus.NOT_FOUND);

    verify(companyService).saveOrganizationLogo(eq(orgId), ArgumentMatchers.any());
  }

  @DisplayName("GET /orgs/{id}/profile/logo should return 404")
  @Test
  void testGetOrgLogo_NotFound() {
    var orgId = UUID.randomUUID();
    when(companyService.getOrganizationLogo(eq(orgId))).thenReturn(Optional.empty());

    given()
        .accept("image/*")
        .when()
        .get("/orgs/{id}/profile/logo", orgId)
        .then()
        .status(HttpStatus.NOT_FOUND);

    verify(companyService).getOrganizationLogo(eq(orgId));
  }

  @DisplayName("GET /orgs/{id}/profile/logo should return 200")
  @Test
  void testGetOrgLogo() throws Exception {
    var orgId = UUID.randomUUID();
    var file = ResourceUtils.getFile("classpath:img/wikipedia.png");
    var bytes = Files.readAllBytes(file.toPath());
    var is = new ByteArrayInputStream(bytes);
    var companyLogo = new CompanyLogo(UUID.randomUUID(), 12315L, "image/png");
    when(companyService.getOrganizationLogo(eq(orgId))).thenReturn(Optional.of(companyLogo));
    when(companyService.getOrganizationLogoAsStream(eq(companyLogo))).thenReturn(is);

    var byteResponse = given()
        .accept("image/*")
        .when()
        .get("/orgs/{id}/profile/logo", orgId)
        .then()
        .status(HttpStatus.OK)
            .extract()
                .asByteArray();

    assertArrayEquals(bytes, byteResponse);

    verify(companyService).getOrganizationLogo(eq(orgId));
    verify(companyService).getOrganizationLogoAsStream(eq(companyLogo));
  }

  @DisplayName("DELETE /orgs/{id}/profile/logo should return 204")
  @Test
  void testDeleteOrgLogo() {
    var orgId = UUID.randomUUID();

    given()
    .when()
      .delete("/orgs/{id}/profile/logo", orgId)
    .then()
      .statusCode(204);

    verify(this.companyService).deleteOrganizationLogo(eq(orgId));
  }
}