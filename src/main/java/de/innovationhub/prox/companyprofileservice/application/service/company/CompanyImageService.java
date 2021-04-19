package de.innovationhub.prox.companyprofileservice.application.service.company;

import de.innovationhub.prox.companyprofileservice.application.service.core.CrudService;
import de.innovationhub.prox.companyprofileservice.domain.company.Company;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public interface CompanyImageService extends CrudService<CompanyImage, UUID> {
  InputStream getCompanyImage(CompanyImage companyImage);
  CompanyImage setCompanyImage(CompanyImage companyImage, InputStream inputStream)
      throws IOException;
  CompanyImage deleteCompanyImage(CompanyImage companyImage);
}
