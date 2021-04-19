package de.innovationhub.prox.companyprofileservice.application.service.company;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import de.innovationhub.prox.companyprofileservice.domain.company.CompanyImage;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyImageRepository;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyImageStore;
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

@SpringBootTest(classes = {CompanyImageServiceImpl.class})
class CompanyImageServiceImplTest {

  @MockBean
  CompanyImageStore companyImageStore;

  @MockBean
  CompanyImageRepository companyImageRepository;

  @Autowired
  CompanyImageService companyImageService;

  private InputStream resourceImage;

  @BeforeEach
  void setup() throws IOException {
    this.resourceImage = new FileInputStream(ResourceUtils.getFile("classpath:img/wikipedia.png"));
  }

  @Test
  void getCompanyImage() throws IOException {
    CompanyImage companyImage = new CompanyImage(UUID.randomUUID(), 4567L, "image/png");
    when(companyImageStore.getContent(any())).thenReturn(resourceImage);

    this.companyImageService.getCompanyImage(companyImage);

    verify(companyImageStore).getContent(eq(companyImage));
  }

  @Test
  void setCompanyImage() throws IOException {
    CompanyImage companyImage = new CompanyImage(UUID.randomUUID(), 4567L, "");
    when(companyImageStore.setContent(any(), any(InputStream.class))).thenAnswer(invocation -> invocation.getArgument(0));
    when(companyImageRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    this.companyImageService.setCompanyImage(companyImage, resourceImage);

    companyImage.setMimeType("image/png");
    verify(companyImageStore).setContent(eq(companyImage), eq(resourceImage));
  }

  @Test
  void deleteCompanyImage() {
    CompanyImage companyImage = new CompanyImage(UUID.randomUUID(), 4567L, "image/png");
    when(companyImageStore.unsetContent(any())).thenAnswer(invocation -> invocation.getArgument(0));

    this.companyImageService.deleteCompanyImage(companyImage);

    verify(companyImageStore).unsetContent(eq(companyImage));
  }
}