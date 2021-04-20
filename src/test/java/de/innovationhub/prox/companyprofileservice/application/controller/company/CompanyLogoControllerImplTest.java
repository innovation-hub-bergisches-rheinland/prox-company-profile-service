package de.innovationhub.prox.companyprofileservice.application.controller.company;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.innovationhub.prox.companyprofileservice.application.service.company.CompanyLogoService;
import de.innovationhub.prox.companyprofileservice.application.service.company.CompanyService;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyLogo;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.UUID;
import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(controllers = CompanyLogoController.class)
class CompanyLogoControllerImplTest {

  @MockBean
  CompanyService companyService;

  @MockBean
  CompanyLogoService companyLogoService;

  @Autowired
  WebApplicationContext context;

  private File file;

  @BeforeEach
  void setup() throws IOException {
    file = ResourceUtils.getFile("classpath:img/wikipedia.png");
  }

  @DisplayName("GET /companies/{id}/image should return OK")
  @Test
  void getCompanyLogoShouldReturnOk() throws NoSuchAlgorithmException {
    byte[] bytes = new byte[20];
    SecureRandom.getInstanceStrong().nextBytes(bytes);
    InputStream is = new ByteArrayInputStream(bytes);
    CompanyLogo companyLogo = new CompanyLogo(UUID.randomUUID(), 12315L, "image/png");
    when(companyService.getCompanyLogo(any())).thenReturn(Optional.of(companyLogo));
    when(companyLogoService.getCompanyLogoAsStream(any())).thenReturn(Optional.of(is));

    given()
        .webAppContextSetup(context)
        .header("Accept", "image/*")
        .when()
        .get("/companies/{id}/image", UUID.randomUUID())
        .then()
        .status(HttpStatus.OK);

    verify(companyService).getCompanyLogo(any());
  }

  @DisplayName("GET /companies/{id}/image should return NOT_FOUND")
  @Test
  void getCompanyLogoShouldReturnNotFound() throws NoSuchAlgorithmException {
    when(companyService.getCompanyLogo(any())).thenReturn(Optional.empty());

    given()
        .webAppContextSetup(context)
        .header("Accept", "image/*")
        .when()
        .get("/companies/{id}/image", UUID.randomUUID())
        .then()
        .status(HttpStatus.NOT_FOUND);

    verify(companyService).getCompanyLogo(any());
  }

  @DisplayName("POST /companies/{id}/image should return OK")
  @Test
  void postCompanyLogoShouldReturnOk() throws IOException {
    CompanyLogo companyLogo = new CompanyLogo(UUID.randomUUID(), 12315L, "image/png");
    when(companyService.setCompanyLogo(any(), any(InputStream.class))).thenReturn(Optional.of(companyLogo));

    given()
        .webAppContextSetup(context)
        .header("Accept", "image/*")
        .multiPart("image", file)
        .when()
        .post("/companies/{id}/image", UUID.randomUUID())
        .then()
        .status(HttpStatus.OK);

    verify(companyService).setCompanyLogo(any(), any(InputStream.class));
  }

  @DisplayName("POST /companies/{id}/image should return INTERNAL_SERVER_ERROR")
  @Test
  void postCompanyLogoShouldReturnInternalServerError() throws IOException {
    when(companyService.setCompanyLogo(any(), any(InputStream.class))).thenReturn(Optional.empty());

    given()
        .webAppContextSetup(context)
        .header("Accept", "image/*")
        .multiPart("image", file)
        .when()
        .post("/companies/{id}/image", UUID.randomUUID())
        .then()
        .status(HttpStatus.INTERNAL_SERVER_ERROR);

    verify(companyService).setCompanyLogo(any(), any(InputStream.class));
  }

  @DisplayName("DELETE /companies/{id}/image should return NO_CONTENT")
  @Test
  void postCompanyLogoShouldReturnNoContent() throws IOException {
    CompanyLogo companyLogo = new CompanyLogo(UUID.randomUUID(), 12315L, "image/png");
    when(companyService.deleteCompanyLogo(any())).thenReturn(Optional.of(companyLogo));

    given()
        .webAppContextSetup(context)
        .header("Accept", "image/*")
        .when()
        .delete("/companies/{id}/image", UUID.randomUUID())
        .then()
        .status(HttpStatus.NO_CONTENT);

    verify(companyService).deleteCompanyLogo(any());
  }

  @DisplayName("DELETE /companies/{id}/image should return NOT_FOUND")
  @Test
  void postCompanyLogoShouldReturnNotFound() throws IOException {
    CompanyLogo companyLogo = new CompanyLogo(UUID.randomUUID(), 12315L, "image/png");
    when(companyService.deleteCompanyLogo(any())).thenReturn(Optional.empty());

    given()
        .webAppContextSetup(context)
        .header("Accept", "image/*")
        .when()
        .delete("/companies/{id}/image", UUID.randomUUID())
        .then()
        .status(HttpStatus.NOT_FOUND);

    verify(companyService).deleteCompanyLogo(any());
  }
}