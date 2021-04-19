package de.innovationhub.prox.companyprofileservice.application.service.company;

import de.innovationhub.prox.companyprofileservice.application.service.core.CrudService;
import de.innovationhub.prox.companyprofileservice.domain.company.Company;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyLogo;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public interface CompanyLogoService extends CrudService<CompanyLogo, UUID> {
  InputStream getCompanyLogo(CompanyLogo companyLogo);
  CompanyLogo setCompanyLogo(CompanyLogo companyLogo, InputStream inputStream)
      throws IOException;
  CompanyLogo deleteCompanyLogo(CompanyLogo companyLogo);
}
