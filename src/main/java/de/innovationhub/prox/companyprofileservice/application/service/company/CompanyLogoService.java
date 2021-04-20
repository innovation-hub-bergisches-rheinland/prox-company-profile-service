package de.innovationhub.prox.companyprofileservice.application.service.company;

import de.innovationhub.prox.companyprofileservice.domain.company.CompanyLogo;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

public interface CompanyLogoService {
  Optional<CompanyLogo> getCompanyLogo(UUID companyId);
  Optional<InputStream> getCompanyLogoAsStream(CompanyLogo companyLogo);
  Optional<CompanyLogo> setCompanyLogo(UUID companyId, InputStream inputStream) throws IOException;
  Optional<CompanyLogo> deleteCompanyLogo(UUID companyId);
}
