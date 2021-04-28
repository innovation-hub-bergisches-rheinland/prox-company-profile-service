package de.innovationhub.prox.companyprofileservice.application.service.company;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.innovationhub.prox.companyprofileservice.domain.company.Company;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyRepository;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanySampleData;
import de.innovationhub.prox.companyprofileservice.domain.company.language.LanguageRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

@Import({CompanyServiceImpl.class})
@SpringBootTest
class CompanyServiceImplTest {

  @MockBean CompanyRepository companyRepository;

  @MockBean LanguageRepository languageRepository;

  @MockBean CompanyLogoService companyLogoService;

  @Autowired CompanyService companyService;

  Company sampleCompany;
  Company sampleCompany2;
  Iterable<Company> sampleCompanies;

  @BeforeEach
  void setUp() {
    var companySampleData = new CompanySampleData();
    this.sampleCompany = companySampleData.getSAMPLE_COMPANY_1();
    this.sampleCompany2 = companySampleData.getSAMPLE_COMPANY_2();
    this.sampleCompanies = companySampleData.getSAMPLE_COMPANIES();
  }

  @DisplayName("Should return all companies")
  @Test
  void shouldReturnAllCompanies() {
    when(companyRepository.findAll()).thenReturn(sampleCompanies);

    assertThat(companyService.getAll()).containsExactlyInAnyOrderElementsOf(sampleCompanies);

    verify(companyRepository).findAll();
  }

  @DisplayName("Should return company")
  @Test
  void shouldReturnCompany() {
    when(companyRepository.findById(eq(sampleCompany.getId())))
        .thenReturn(Optional.of(sampleCompany));

    assertThat(companyService.getById(sampleCompany.getId())).get().isEqualTo(sampleCompany);

    verify(companyRepository).findById(eq(sampleCompany.getId()));
  }

  @DisplayName("Should return empty optional")
  @Test
  void shouldReturnEmptyOptional() {
    when(companyRepository.findById(any())).thenReturn(Optional.empty());

    assertThat(companyService.getById(sampleCompany.getId())).isEmpty();

    verify(companyRepository).findById(eq(sampleCompany.getId()));
  }

  @DisplayName("Should save company")
  @Test
  void shouldSaveCompany() {
    when(companyRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    assertThat(companyService.save(sampleCompany)).isEqualTo(sampleCompany);

    verify(companyRepository).save(eq(sampleCompany));
  }

  @DisplayName("Should update company")
  @Test
  void shouldUpdateCompany() {
    when(companyRepository.findById(eq(sampleCompany.getId())))
        .thenReturn(Optional.of(sampleCompany));
    when(companyRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    var updated = companyService.update(sampleCompany.getId(), sampleCompany2);

    // Verify that id is not updated
    assertThat(updated.getId()).isEqualTo(sampleCompany.getId());

    // TODO: Test that the other properties were set

    verify(companyRepository).findById(eq(sampleCompany.getId()));
    verify(companyRepository).save(any());
  }

  @DisplayName("Should delete company")
  @Test
  void shouldDeleteCompany() {
    when(companyRepository.findById(eq(sampleCompany.getId())))
        .thenReturn(Optional.of(sampleCompany));

    companyService.deleteById(sampleCompany.getId());

    verify(companyRepository).deleteById(eq(sampleCompany.getId()));
  }
}
