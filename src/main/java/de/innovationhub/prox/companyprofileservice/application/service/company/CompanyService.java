package de.innovationhub.prox.companyprofileservice.application.service.company;

import de.innovationhub.prox.companyprofileservice.application.service.core.CrudService;
import de.innovationhub.prox.companyprofileservice.domain.company.Company;
import de.innovationhub.prox.companyprofileservice.domain.company.language.Language;
import java.util.Collections;
import java.util.UUID;

public interface CompanyService extends CrudService<Company, UUID> {

  default void deleteCompany(Company company) {
    this.deleteById(company.getId());
  }

  default Iterable<Language> getCompanyLanguages(UUID id) {
    return this.getById(id).map(Company::getLanguages).orElse(Collections.emptySet());
  }

  Company setCompanyLanguages(UUID id, Iterable<UUID> languageIds);

}
