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
    CompanyLogo companyLogo = new CompanyLogo(UUID.randomUUID(), 4567L, "image/png");
    when(companyLogoStore.getContent(any())).thenReturn(resourceImage);

    this.companyLogoService.getCompanyLogo(companyLogo);

    verify(companyLogoStore).getContent(eq(companyLogo));
  }

  @Test
  void setCompanyLogo() throws IOException {
    CompanyLogo companyLogo = new CompanyLogo(UUID.randomUUID(), 4567L, "");
    when(companyLogoStore.setContent(any(), any(InputStream.class))).thenAnswer(invocation -> invocation.getArgument(0));
    when(companyLogoRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    this.companyLogoService.setCompanyLogo(companyLogo, resourceImage);

    companyLogo.setMimeType("image/png");
    verify(companyLogoStore).setContent(eq(companyLogo), any(InputStream.class));
  }

  @Test
  void deleteCompanyLogo() {
    CompanyLogo companyLogo = new CompanyLogo(UUID.randomUUID(), 4567L, "image/png");
    when(companyLogoStore.unsetContent(any())).thenAnswer(invocation -> invocation.getArgument(0));

    this.companyLogoService.deleteCompanyLogo(companyLogo);

    verify(companyLogoStore).unsetContent(eq(companyLogo));
  }
}