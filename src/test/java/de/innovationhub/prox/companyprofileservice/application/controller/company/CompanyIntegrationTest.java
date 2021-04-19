package de.innovationhub.prox.companyprofileservice.application.controller.company;

import static io.restassured.RestAssured.withArgs;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import de.innovationhub.prox.companyprofileservice.domain.company.Branch;
import de.innovationhub.prox.companyprofileservice.domain.company.Company;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyInformation;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyRepository;
import de.innovationhub.prox.companyprofileservice.domain.company.language.Language;
import de.innovationhub.prox.companyprofileservice.domain.company.language.LanguageRepository;
import de.innovationhub.prox.companyprofileservice.domain.company.language.Type;
import de.innovationhub.prox.companyprofileservice.domain.company.quarter.Quarter;
import de.innovationhub.prox.companyprofileservice.domain.company.quarter.QuarterRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class CompanyIntegrationTest {

  @Autowired WebApplicationContext context;

  @Autowired LanguageRepository languageRepository;

  @Autowired CompanyRepository companyRepository;

  @Autowired
  QuarterRepository quarterRepository;

  @DisplayName("Should create company")
  @Transactional
  @Test
  void shouldCreateCompany() {
    // Create Company
    var company =
        new Company(
            new CompanyInformation(
                "Company Inc", "19-04-2021", "about 200", "www.example.org", "Lorem Ipsum"));
    var branch = new Branch("Automotive");
    var branches = Set.of(branch);
    company.setBranches(branches);

    var companyResponse =
        given()
            .webAppContextSetup(context)
            .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .header("Accept", MediaTypes.HAL_JSON_VALUE)
            .body(company)
            .when()
            .post("/companies")
            .then()
            .log()
            .all()
            .status(HttpStatus.CREATED)
            .body("information.name", equalTo(company.getInformation().getName()))
            .body("information.foundingDate", equalTo(company.getInformation().getFoundingDate()))
            .body(
                "information.numberOfEmployees",
                equalTo(company.getInformation().getNumberOfEmployees()))
            .body("information.homepage", equalTo(company.getInformation().getHomepage()))
            .body("information.vita", equalTo(company.getInformation().getVita()))
            .body("branches[0].branchName", equalTo(branch.getBranchName()));

    var companyId = companyResponse.extract().jsonPath().getUUID("id");

    assertThat(companyRepository.findById(companyId).get()).isEqualTo(company);
  }

  @DisplayName("Should update company")
  @Transactional
  @Test
  void shouldUpdateCompany() {
    // Create Company
    var company =
        new Company(
            new CompanyInformation(
                "Company Inc", "19-04-2021", "about 200", "www.example.org", "Lorem Ipsum"));
    var branch = new Branch("Automotive");
    var branches = Set.of(branch);
    company.setBranches(branches);
    companyRepository.save(company);

    //Update Company
    company.setBranches(Set.of());
    company.setInformation(new CompanyInformation("Company Inc 2", "Today", null, null, null));

    var companyResponse =
        given()
            .webAppContextSetup(context)
            .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .header("Accept", MediaTypes.HAL_JSON_VALUE)
            .body(company)
            .when()
            .put("/companies/{id}", company.getId().toString())
            .then()
            .log()
            .all()
            .status(HttpStatus.OK)
            .body("information.name", equalTo(company.getInformation().getName()))
            .body("information.foundingDate", equalTo(company.getInformation().getFoundingDate()))
            .body(
                "information.numberOfEmployees",
                equalTo(company.getInformation().getNumberOfEmployees()))
            .body("information.homepage", equalTo(company.getInformation().getHomepage()))
            .body("information.vita", equalTo(company.getInformation().getVita()))
            .body("branches", hasSize(0));

    var companyId = companyResponse.extract().jsonPath().getUUID("id");

    assertThat(companyRepository.findById(companyId)).get().isEqualTo(company);
  }

  @DisplayName("Should set company languages")
  @Transactional
  @Test
  void shouldSetCompanyLanguages() {
    // Create Company
    var company =
        new Company(
            new CompanyInformation(
                "Company Inc", "19-04-2021", "about 200", "www.example.org", "Lorem Ipsum"));
    companyRepository.save(company);

    // Save languages
    var language1 = new Language("de", "German", "Deutsch", Type.LIVING);
    languageRepository.save(language1);

    // Put languages
    given()
        .webAppContextSetup(context)
        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
        .body(new String[] {language1.getId().toString()})
        .when()
        .put("/companies/{id}/languages", company.getId().toString())
        .then()
        .log()
        .all()
        .status(HttpStatus.OK)
        .rootPath("_embedded.languageList.find { it.id == '%s' }")
        .body(
            "isoIdentifier2",
            withArgs(language1.getId().toString()),
            is(language1.getIsoIdentifier2()))
        .body("englishName", withArgs(language1.getId().toString()), is(language1.getEnglishName()))
        .body("germanName", withArgs(language1.getId().toString()), is(language1.getGermanName()))
        .body("type", withArgs(language1.getId().toString()), is(language1.getType().toString()));

    assertThat(companyRepository.findById(company.getId()).get().getLanguages()).containsExactlyInAnyOrder(language1);
  }

  @DisplayName("Should create quarter")
  @Transactional
  @Test
  void shouldCreateQuarter() {
    // Create Headquarter
    var quarter = new Quarter("Germany");

    UUID quarterId =
        given()
            .webAppContextSetup(context)
            .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .header("Accept", MediaTypes.HAL_JSON_VALUE)
            .body(quarter)
            .when()
            .post("/quarters")
            .then()
            .status(HttpStatus.CREATED)
            .body("location", is(quarter.getLocation()))
            .extract()
            .jsonPath()
            .getUUID("id");

    assertThat(quarterRepository.findById(quarterId).get()).isEqualTo(quarter);
  }

  @DisplayName("Should delete quarter")
  @Transactional
  @Test
  void shouldDeleteQuarter() {
    // Create Headquarter
    var quarter = new Quarter("Germany");
    quarter = quarterRepository.save(quarter);

        given()
            .webAppContextSetup(context)
            .when()
            .delete("/quarters/{id}", quarter.getId().toString())
            .then()
            .status(HttpStatus.NO_CONTENT);

    assertThat(quarterRepository.findById(quarter.getId())).isEmpty();
  }

  @DisplayName("Should update quarter")
  @Transactional
  @Test
  void shouldUpdateQuarter() {
    // Create Headquarter
    var quarter = new Quarter("Germany");
    quarter = quarterRepository.save(quarter);
    quarter.setLocation("England");

    UUID quarterId =
        given()
            .webAppContextSetup(context)
            .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .header("Accept", MediaTypes.HAL_JSON_VALUE)
            .body(quarter)
            .when()
            .put("/quarters/{id}", quarter.getId().toString())
            .then()
            .status(HttpStatus.OK)
            .body("location", is(quarter.getLocation()))
            .extract()
            .jsonPath()
            .getUUID("id");

    assertThat(quarterRepository.findById(quarterId).get()).isEqualTo(quarter);
  }

  @DisplayName("Should set headquarter")
  @Transactional
  @Test
  void shouldSetCompanyHeadquarter() {
    // Create Company
    var company =
        new Company(
            new CompanyInformation(
                "Company Inc", "19-04-2021", "about 200", "www.example.org", "Lorem Ipsum"));
    company = companyRepository.save(company);

    // Create Headquarter
    var headquarter = new Quarter("Germany");
    headquarter = quarterRepository.save(headquarter);

    // Put Headquarter
    given()
        .webAppContextSetup(context)
        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
        .body("{\"id\":\"" + headquarter.getId().toString() + "\"}")
        .when()
        .put("/companies/{id}/headquarter", company.getId())
        .then()
        .log()
        .all()
        .status(HttpStatus.OK)
        .body("id", is(headquarter.getId().toString()))
        .body("location", is(headquarter.getLocation()));

    assertThat(companyRepository.findById(company.getId()).get().getHeadquarter()).isEqualTo(headquarter);
  }

  @DisplayName("Should set quarters")
  @Transactional
  @Test
  void shouldSetCompanyQuarters() {
    // Create Company
    var company =
        new Company(
            new CompanyInformation(
                "Company Inc", "19-04-2021", "about 200", "www.example.org", "Lorem Ipsum"));
    company = companyRepository.save(company);

    // Create Headquarter
    var quarter1 = new Quarter("Germany");
    var quarter2 = new Quarter("England");
    quarter1 = quarterRepository.save(quarter1);
    quarter2 = quarterRepository.save(quarter2);

    // Put Quarters
    given()
        .webAppContextSetup(context)
        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
        .body(Arrays.asList(quarter1.getId().toString(), quarter2.getId().toString()))
        .when()
        .put("/companies/{id}/quarters", company.getId())
        .then()
        .log()
        .all()
        .status(HttpStatus.OK)
        .rootPath("_embedded.quarterList.find { it.id == '%s' }")
        .body("id", withArgs(quarter1.getId().toString()), is(quarter1.getId().toString()))
        .body("location", withArgs(quarter1.getId().toString()), is(quarter1.getLocation()))
        .body("id", withArgs(quarter2.getId().toString()), is(quarter2.getId().toString()))
        .body("location", withArgs(quarter2.getId().toString()), is(quarter2.getLocation()));


    assertThat(companyRepository.findById(company.getId()).get().getQuarters()).containsExactlyInAnyOrder(quarter1, quarter2);
  }

  @DisplayName("Workflow company creation")
  @Transactional
  @Test
  void companyCreationShouldBeSuccessful() {
    // Create Company
    var company =
        new Company(
            new CompanyInformation(
                "Company Inc", "19-04-2021", "about 200", "www.example.org", "Lorem Ipsum"));
    var branch = new Branch("Automotive");
    var branches = Set.of(branch);
    company.setBranches(branches);

    var companyResponse =
        given()
            .webAppContextSetup(context)
            .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .header("Accept", MediaTypes.HAL_JSON_VALUE)
            .body(company)
            .when()
            .post("/companies")
            .then()
            .log()
            .all()
            .status(HttpStatus.CREATED)
            .body("information.name", equalTo(company.getInformation().getName()))
            .body("information.foundingDate", equalTo(company.getInformation().getFoundingDate()))
            .body(
                "information.numberOfEmployees",
                equalTo(company.getInformation().getNumberOfEmployees()))
            .body("information.homepage", equalTo(company.getInformation().getHomepage()))
            .body("information.vita", equalTo(company.getInformation().getVita()))
            .body("branches[0].branchName", equalTo(branch.getBranchName()));

    var companyId = companyResponse.extract().jsonPath().getUUID("id");
    var linkOfLanguages = companyResponse.extract().jsonPath().getString("_links.languages.href");
    var linkOfHeadquarter =
        companyResponse.extract().jsonPath().getString("_links.headquarter.href");
    var linkOfQuarters = companyResponse.extract().jsonPath().getString("_links.quarters.href");

    // Save languages
    var language1 = new Language("de", "German", "Deutsch", Type.LIVING);
    var language2 = new Language("en", "English", "Englisch", Type.LIVING);
    languageRepository.saveAll(Arrays.asList(language1, language2));

    // Put languages
    given()
        .webAppContextSetup(context)
        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
        .body(new String[] {language1.getId().toString(), language2.getId().toString()})
        .when()
        .put(linkOfLanguages)
        .then()
        .log()
        .all()
        .status(HttpStatus.OK)
        .rootPath("_embedded.languageList.find { it.id == '%s' }")
        .body(
            "isoIdentifier2",
            withArgs(language1.getId().toString()),
            is(language1.getIsoIdentifier2()))
        .body("englishName", withArgs(language1.getId().toString()), is(language1.getEnglishName()))
        .body("germanName", withArgs(language1.getId().toString()), is(language1.getGermanName()))
        .body("type", withArgs(language1.getId().toString()), is(language1.getType().toString()))
        .body(
            "isoIdentifier2",
            withArgs(language2.getId().toString()),
            is(language2.getIsoIdentifier2()))
        .body("englishName", withArgs(language2.getId().toString()), is(language2.getEnglishName()))
        .body("germanName", withArgs(language2.getId().toString()), is(language2.getGermanName()))
        .body("type", withArgs(language2.getId().toString()), is(language2.getType().toString()));

    // Create Headquarter
    var headquarter = new Quarter("Germany");

    String strHeadquarterId =
        given()
            .webAppContextSetup(context)
            .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .header("Accept", MediaTypes.HAL_JSON_VALUE)
            .body(headquarter)
            .when()
            .post("/quarters")
            .then()
            .status(HttpStatus.CREATED)
            .body("location", is(headquarter.getLocation()))
            .extract()
            .jsonPath()
            .getString("id");

    // Put Headquarter
    given()
        .webAppContextSetup(context)
        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
        .body("{\"id\":\"" + strHeadquarterId + "\"}")
        .when()
        .put(linkOfHeadquarter)
        .then()
        .log()
        .all()
        .status(HttpStatus.OK)
        .body("id", is(strHeadquarterId))
        .body("location", is(headquarter.getLocation()));

    // Create Quarter
    var quarter = new Quarter("England");

    String strQuarterId =
        given()
            .webAppContextSetup(context)
            .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .header("Accept", MediaTypes.HAL_JSON_VALUE)
            .body(quarter)
            .when()
            .post("/quarters")
            .then()
            .status(HttpStatus.CREATED)
            .body("location", is(quarter.getLocation()))
            .extract()
            .jsonPath()
            .getString("id");

    // Put Quarter
    given()
        .webAppContextSetup(context)
        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
        .body(Collections.singletonList(strQuarterId))
        .when()
        .put(linkOfQuarters)
        .then()
        .log()
        .all()
        .status(HttpStatus.OK)
        .rootPath("_embedded.quarterList.find { it.id == '%s' }")
        .body("id", withArgs(strQuarterId), is(quarter.getId().toString()))
        .body("location", withArgs(quarter.getId().toString()), is(quarter.getLocation()));

    company.setLanguages(Set.of(language1, language2));
    company.setQuarters(Set.of(quarter));
    company.setHeadquarter(headquarter);

    assertThat(companyRepository.findById(companyId)).get().isEqualTo(company);
  }
}
