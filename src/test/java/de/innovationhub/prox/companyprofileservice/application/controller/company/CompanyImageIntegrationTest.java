package de.innovationhub.prox.companyprofileservice.application.controller.company;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

import de.innovationhub.prox.companyprofileservice.application.service.company.CompanyImageService;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyImage;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyImageRepository;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyImageStore;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyRepository;
import de.innovationhub.prox.companyprofileservice.domain.company.language.LanguageRepository;
import de.innovationhub.prox.companyprofileservice.domain.company.quarter.QuarterRepository;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(
    classes = {
        CompanyImageControllerImpl.class,
        CompanyImageService.class,
        CompanyImageStore.class,
        CompanyImageRepository.class
    }
)
class CompanyImageIntegrationTest {

  @Autowired
  WebApplicationContext context;

  @Autowired
  CompanyImageRepository companyImageRepository;

  @Autowired
  CompanyImageStore companyImageStore;

  @Autowired
  CompanyImageService companyImageService;

  @MockBean
  CompanyRepository companyRepository;

  private File file;

  @BeforeEach
  void setup() throws IOException {
    file = ResourceUtils.getFile("classpath:img/wikipedia.png");
  }

  @DisplayName("Should get image")
  @Test
  void shouldGetImage() throws IOException {
    //TODO write test
  }

  @DisplayName("Should save image")
  @Test
  void shouldSaveImage() throws IOException {
    //TODO write test
  }

  @DisplayName("Should delete image")
  @Test
  void shouldDeleteImage() throws IOException {
    //TODO write test
  }

}
