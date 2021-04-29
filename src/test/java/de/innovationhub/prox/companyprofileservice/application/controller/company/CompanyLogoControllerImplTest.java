package de.innovationhub.prox.companyprofileservice.application.controller.company;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.innovationhub.prox.companyprofileservice.application.config.KeycloakConfig;
import de.innovationhub.prox.companyprofileservice.application.service.company.CompanyLogoService;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyLogo;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

//TODO: This test breaks the jenkins build
/*@SpringBootTest
class CompanyLogoControllerImplTest {

  @MockBean
  CompanyLogoService companyLogoService;

  @Autowired
  WebApplicationContext context;

  @DisplayName("GET /companies/{id}/image should return OK")
  @Test
  void getCompanyLogoShouldReturnOk() throws NoSuchAlgorithmException {
    byte[] bytes = new byte[20];
    SecureRandom.getInstanceStrong().nextBytes(bytes);
    InputStream is = new ByteArrayInputStream(bytes);
    CompanyLogo companyLogo = new CompanyLogo(UUID.randomUUID(), 12315L, "image/png");
    when(companyLogoService.getCompanyLogo(any())).thenReturn(Optional.of(companyLogo));
    when(companyLogoService.getCompanyLogoAsStream(any())).thenReturn(Optional.of(is));

    given()
        .webAppContextSetup(context)
        .header("Accept", "image/*")
        .when()
        .get("/companies/{id}/logo", UUID.randomUUID())
        .then()
        .status(HttpStatus.OK);

    verify(companyLogoService).getCompanyLogo(any());
    verify(companyLogoService).getCompanyLogoAsStream(any());
  }

  @DisplayName("GET /companies/{id}/image should return NOT_FOUND")
  @Test
  void getCompanyLogoShouldReturnNotFound() throws NoSuchAlgorithmException {
    when(companyLogoService.getCompanyLogo(any())).thenReturn(Optional.empty());

    given()
        .webAppContextSetup(context)
        .header("Accept", "image/*")
        .when()
        .get("/companies/{id}/logo", UUID.randomUUID())
        .then()
        .status(HttpStatus.NOT_FOUND);

    verify(companyLogoService).getCompanyLogo(any());
  }

  @DisplayName("POST /companies/{id}/image should return OK")
  @Test
  void postCompanyLogoShouldReturnOk() throws IOException {
    CompanyLogo companyLogo = new CompanyLogo(UUID.randomUUID(), 12315L, "image/png");
    when(companyLogoService.setCompanyLogo(any(), any(InputStream.class)))
        .thenReturn(Optional.of(companyLogo));

    File file = ResourceUtils.getFile("classpath:img/wikipedia.png");

    given()
        .webAppContextSetup(context)
        .header("Accept", "image/*")
        .multiPart("image", file)
        .when()
        .post("/companies/{id}/logo", UUID.randomUUID())
        .then()
        .status(HttpStatus.OK);

    verify(companyLogoService).setCompanyLogo(any(), any(InputStream.class));
  }

  @DisplayName("POST /companies/{id}/image should return INTERNAL_SERVER_ERROR")
  @Test
  void postCompanyLogoShouldReturnInternalServerError() throws IOException {
    when(companyLogoService.setCompanyLogo(any(), any(InputStream.class)))
        .thenReturn(Optional.empty());

    File file = ResourceUtils.getFile("classpath:img/wikipedia.png");

    given()
        .webAppContextSetup(context)
        .header("Accept", "image/*")
        .multiPart("image", file)
        .when()
        .post("/companies/{id}/logo", UUID.randomUUID())
        .then()
        .status(HttpStatus.INTERNAL_SERVER_ERROR);

    verify(companyLogoService).setCompanyLogo(any(), any(InputStream.class));
  }

  @DisplayName("DELETE /companies/{id}/image should return NO_CONTENT")
  @Test
  void postCompanyLogoShouldReturnNoContent() throws IOException {
    CompanyLogo companyLogo = new CompanyLogo(UUID.randomUUID(), 12315L, "image/png");
    when(companyLogoService.deleteCompanyLogo(any())).thenReturn(Optional.of(companyLogo));

    given()
        .webAppContextSetup(context)
        .header("Accept", "image/*")
        .when()
        .delete("/companies/{id}/logo", UUID.randomUUID())
        .then()
        .status(HttpStatus.NO_CONTENT);

    verify(companyLogoService).deleteCompanyLogo(any());
  }

  @DisplayName("DELETE /companies/{id}/image should return NOT_FOUND")
  @Test
  void postCompanyLogoShouldReturnNotFound() throws IOException {
    CompanyLogo companyLogo = new CompanyLogo(UUID.randomUUID(), 12315L, "image/png");
    when(companyLogoService.deleteCompanyLogo(any())).thenReturn(Optional.empty());

    given()
        .webAppContextSetup(context)
        .header("Accept", "image/*")
        .when()
        .delete("/companies/{id}/logo", UUID.randomUUID())
        .then()
        .status(HttpStatus.NOT_FOUND);

    verify(companyLogoService).deleteCompanyLogo(any());
  }
}*/