package de.innovationhub.prox.companyprofileservice.application.service.company;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import de.innovationhub.prox.companyprofileservice.domain.company.CompanyLogo;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyLogoRepository;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyLogoStore;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

@SpringBootTest(classes = {CompanyLogoServiceImpl.class})
class CompanyLogoServiceImplTest {

  @MockBean
  CompanyLogoStore companyLogoStore;

  @MockBean
  CompanyLogoRepository companyLogoRepository;

  @Autowired
  CompanyLogoService companyLogoService;

  private InputStream resourceImage;

  @BeforeEach
  void setup() throws IOException {
    this.resourceImage = new FileInputStream(ResourceUtils.getFile("classpath:img/wikipedia.png"));
  }

  @Test
  void getCompanyLogo() throws IOException {

  }

  @Test
  void setCompanyLogo() throws IOException {

  }

  @Test
  void deleteCompanyLogo() {

  }
}