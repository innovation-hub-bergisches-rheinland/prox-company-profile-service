package de.innovationhub.prox.companyprofileservice.application.service.company;

import de.innovationhub.prox.companyprofileservice.application.service.core.CrudService;
import de.innovationhub.prox.companyprofileservice.domain.company.Company;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyLogo;
import de.innovationhub.prox.companyprofileservice.domain.company.language.Language;
import de.innovationhub.prox.companyprofileservice.domain.company.quarter.Quarter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.util.Pair;

public interface CompanyService extends CrudService<Company, UUID> {

  default void deleteCompany(Company company) {
    this.deleteById(company.getId());
  }

  default Iterable<Language> getCompanyLanguages(UUID id) {
    return this.getById(id).map(Company::getLanguages).orElse(Collections.emptySet());
  }

  Company setCompanyLanguages(UUID id, Iterable<UUID> languageIds);

  Iterable<Quarter> getCompanyQuarters(UUID id);

  Iterable<Quarter> setCompanyQuarters(UUID id, Iterable<UUID> quarterIds);

  Optional<Quarter> getCompanyHeadquarter(UUID id);

  Quarter setCompanyHeadquarter(UUID id, UUID quarterId);

  Optional<CompanyLogo> getCompanyLogo(UUID companyId);
  Optional<CompanyLogo> setCompanyLogo(UUID companyId, InputStream inputStream) throws IOException;
  Optional<CompanyLogo> deleteCompanyLogo(UUID companyId);
}
