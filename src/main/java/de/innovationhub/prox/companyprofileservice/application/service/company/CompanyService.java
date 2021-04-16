package de.innovationhub.prox.companyprofileservice.application.service.company;

import de.innovationhub.prox.companyprofileservice.application.exception.company.CompanyNotFoundException;
import de.innovationhub.prox.companyprofileservice.domain.company.Company;
import de.innovationhub.prox.companyprofileservice.domain.language.Language;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyService {
  Iterable<Company> getAllCompanies();

  Optional<Company> getCompanyById(UUID id);

  Company saveCompany(Company company);

  Company updateCompany(Company company);

  default void deleteCompany(Company company) {
    this.deleteCompanyById(company.getId());
  }
  void deleteCompanyById(UUID id);

  default Iterable<Language> getCompanyLanguages(UUID id) {
    return this.getCompanyById(id).map(Company::getLanguages).orElse(Collections.emptyList());
  }

  default Company setCompanyLanguages(UUID id, List<Language> languages) {
    return this.getCompanyById(id)
        .map(company -> {
          company.setLanguages(languages);
          return company;
        })
        .orElseThrow(CompanyNotFoundException::new);
  }
}
