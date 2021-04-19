package de.innovationhub.prox.companyprofileservice.application.controller.company;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.innovationhub.prox.companyprofileservice.application.service.company.CompanyImageService;
import de.innovationhub.prox.companyprofileservice.application.service.company.CompanyService;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

/*@WebMvcTest(controllers = CompanyImageController.class)
class CompanyImageControllerImplTest {

  @MockBean
  CompanyImageService companyImageService;

  @MockBean
  CompanyService companyService;

  @Autowired
  WebApplicationContext context;

  private File file;

  @BeforeEach
  void setup() throws IOException {
    file = ResourceUtils.getFile("classpath:img/wikipedia.png");
  }

  @DisplayName("GET /companies/{id}/image should return OK")
  @Test
  void getCompanyImageShouldReturnInternalServerError() {
    when(companyImageService.getCompanyImage(any())).thenReturn(mock(InputStream.class));
    when(companyImageService.getById(any())).thenReturn(Optional.of(mock(CompanyImage.class)));

    given()
        .webAppContextSetup(context)
        .header("Accept", "image/*")
        .when()
        .get("/companies/{id}/image", UUID.randomUUID())
        .then()
        .status(HttpStatus.OK);
  }

  @DisplayName("POST /companies/{id}/image should return OK")
  @Test
  void postCompanyImageShouldReturnInternalServerError() throws IOException {
    when(companyImageService.setCompanyImage(any(), any(InputStream.class))).thenAnswer(invocation -> invocation.getArgument(0));
    when(companyImageService.getById(any())).thenReturn(Optional.of(mock(CompanyImage.class)));

    given()
        .webAppContextSetup(context)
        .header("Accept", "image/*")
        .multiPart("image", file)
        .when()
        .post("/companies/{id}/image", UUID.randomUUID())
        .then()
        .status(HttpStatus.OK);

    verify(companyImageService).setCompanyImage(any(), any(InputStream.class));
  }

  @DisplayName("DELETE /companies/{id}/image should return OK")
  @Test
  void deleteCompanyImageShouldReturnInternalServerError() {
    when(companyImageService.deleteCompanyImage(any())).thenAnswer(invocation -> invocation.getArgument(0));
    when(companyImageService.getById(any())).thenReturn(Optional.of(mock(CompanyImage.class)));

    given()
        .webAppContextSetup(context)
        .header("Accept", "image/*")
        .when()
        .delete("/companies/{id}/image", UUID.randomUUID())
        .then()
        .status(HttpStatus.NO_CONTENT);

    verify(companyImageService).deleteCompanyImage(any());
  }
}*/