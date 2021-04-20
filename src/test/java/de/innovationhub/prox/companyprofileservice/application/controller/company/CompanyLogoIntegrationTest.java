package de.innovationhub.prox.companyprofileservice.application.controller.company;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.innovationhub.prox.companyprofileservice.application.service.company.CompanyLogoService;
import de.innovationhub.prox.companyprofileservice.application.service.company.CompanyService;
import de.innovationhub.prox.companyprofileservice.domain.company.Company;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyInformation;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyLogo;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyLogoRepository;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyLogoStore;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyRepository;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanySampleData;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class CompanyLogoIntegrationTest {

  @Autowired
  WebApplicationContext context;

  @Autowired
  CompanyLogoRepository companyLogoRepository;

  @Autowired
  CompanyLogoStore companyLogoStore;

  @Autowired
  CompanyLogoService companyLogoService;

  @Autowired
  CompanyService companyService;

  @Autowired
  CompanyRepository companyRepository;

  private File file;

  @BeforeEach
  void setup() throws IOException {
    file = ResourceUtils.getFile("classpath:img/wikipedia.png");
  }

  @DisplayName("Should get image")
  @Test
  void shouldGetImage() throws IOException {
    //Given
    var company = new Company(new CompanyInformation("Company A"));
    CompanyLogo companyLogo = new CompanyLogo(UUID.randomUUID(), 121415L, "image/png");
    companyLogo = this.companyLogoStore.setContent(companyLogo, new FileInputStream(file));
    companyLogo = this.companyLogoRepository.save(companyLogo);
    company.setCompanyLogo(companyLogo);
    companyRepository.save(company);

    byte[] bytes = given()
        .webAppContextSetup(context)
        .header("Accept", "image/*")
        .when()
        .get("/companies/{id}/image", company.getId().toString())
        .then()
        .status(HttpStatus.OK)
        .header("Content-Type", companyLogo.getMimeType())
        .extract().asByteArray();

    assertArrayEquals(new FileInputStream(file).readAllBytes(), bytes);
  }

  @DisplayName("Should save image")
  @Test
  void shouldSaveImage() throws IOException {
    var company = new Company(new CompanyInformation("Company A"));
    company.setCompanyLogo(null);
    companyRepository.save(company);

    given()
        .webAppContextSetup(context)
        .header("Content-Type", MediaType.MULTIPART_FORM_DATA_VALUE)
        .multiPart("image", file)
        .when()
        .post("/companies/{id}/image", company.getId().toString())
        .then()
        .status(HttpStatus.OK);

    var companylogo = companyService.getCompanyLogo(company.getId());
    var optIs = companyLogoService.getCompanyLogoAsStream(companylogo.get());
    assertArrayEquals(new FileInputStream(file).readAllBytes(), optIs.get().readAllBytes());
  }

  @DisplayName("Should delete image")
  @Test
  void shouldDeleteImage() throws IOException {
    var company = new Company(new CompanyInformation("Company A"));
    var companyLogo = new CompanyLogo(UUID.randomUUID(), 121415L, "image/png");
    companyLogo = companyLogoStore.setContent(companyLogo, new FileInputStream(file));
    companyLogo = companyLogoRepository.save(companyLogo);
    company.setCompanyLogo(companyLogo);
    companyRepository.save(company);

    given()
        .webAppContextSetup(context)
        .when()
        .delete("/companies/{id}/image", company.getId().toString())
        .then()
        .status(HttpStatus.NO_CONTENT);

    assertTrue(companyService.getCompanyLogo(company.getId()).isEmpty());
  }

}
