package de.innovationhub.prox.companyprofileservice.application.service.company;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.innovationhub.prox.companyprofileservice.application.exception.language.LanguageNotFoundException;
import de.innovationhub.prox.companyprofileservice.domain.company.Company;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyRepository;
import de.innovationhub.prox.companyprofileservice.domain.language.Language;
import de.innovationhub.prox.companyprofileservice.domain.language.LanguageRepository;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@Import({
    CompanyServiceImpl.class
})
@SpringBootTest
class CompanyServiceImplTest {

  @MockBean
  CompanyRepository companyRepository;

  @MockBean
  LanguageRepository languageRepository;

  @Autowired
  CompanyService companyService;

  @BeforeEach
  void setUp() {
  }

  @Test
  void getAllCompanies() {
    //No test needed as it is a simple repository call
  }

  @Test
  void getCompanyById() {
    //No test needed as it is a simple repository call
  }

  @Test
  void saveCompany() {
    //No test needed as it is a simple repository call
  }

  @Test
  void updateCompany() {
    //No test needed as it is a simple repository call
  }

  @Test
  void deleteCompanyById() {
    //No test needed as it is a simple repository call
  }

  @Test
  void setCompanyLanguages() {
    //TODO
  }
}