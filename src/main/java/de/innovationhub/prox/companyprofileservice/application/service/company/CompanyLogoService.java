package de.innovationhub.prox.companyprofileservice.application.service.company;

import de.innovationhub.prox.companyprofileservice.application.service.core.CrudService;
import de.innovationhub.prox.companyprofileservice.domain.company.Company;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyLogo;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.util.Pair;

public interface CompanyLogoService extends CrudService<CompanyLogo, UUID> {
  Optional<InputStream> getCompanyLogoAsStream(CompanyLogo companyLogo);
  Optional<CompanyLogo> setCompanyLogo(CompanyLogo companyLogo, InputStream inputStream) throws IOException;
  Optional<CompanyLogo> deleteCompanyLogo(CompanyLogo companyLogo);
}
