package de.innovationhub.prox.companyprofileservice.application.controller.company;

import static io.restassured.RestAssured.withArgs;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
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
import java.util.Set;
import java.util.stream.Collectors;
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

  @DisplayName("Should create company")
  @Transactional
  @Test
  void shouldCreateCompany() {
    // Create Company
    var company =
        new Company(
            new CompanyInformation(
                "Company Inc", "19-04-2021", "about 200", "www.example.org", "Lorem Ipsum"));
    Quarter headquarter = new Quarter("Headquarter");
    var branch = new Branch("Automotive");
    var branches = Set.of(branch);
    company.setBranches(branches);
    company.setHeadquarter(headquarter);
    Quarter quarter = new Quarter("Quarter 1");
    company.setQuarters(Set.of(quarter));

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
            .body("headquarter.location", equalTo(company.getHeadquarter().getLocation()))
            .body("quarters[0].location", equalTo(quarter.getLocation()))
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


}
