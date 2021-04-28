package de.innovationhub.prox.companyprofileservice.application.security;

import de.innovationhub.prox.companyprofileservice.domain.company.Company;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

  private final KeycloakAuthenticationService keycloakAuthenticationService;
  private final CompanyRepository companyRepository;

  @Autowired
  public SecurityService(
      KeycloakAuthenticationService keycloakAuthenticationService,
      CompanyRepository companyRepository) {
    this.keycloakAuthenticationService = keycloakAuthenticationService;
    this.companyRepository = companyRepository;
  }

  public boolean authenticatedUserIsNotOwnerOfAnyCompany() {
    Optional<UUID> subjectId = this.keycloakAuthenticationService.getSubjectId();
    if (subjectId.isEmpty()) {
      throw new RuntimeException("Unauthorized");
    }
    return subjectId.map(companyRepository::existsByCreatorId).orElse(false);
  }

  public boolean authenticatedUserIsOwnerOfCompany(UUID companyId) {
    Optional<UUID> subjectId = this.keycloakAuthenticationService.getSubjectId();
    if (subjectId.isEmpty()) {
      throw new RuntimeException("Unauthorized");
    }
    Optional<Company> company = this.companyRepository.findById(companyId);
    if (company.isEmpty()) {
      throw new RuntimeException("Company does not exist");
    }
    return company.get().getCreatorId().equals(subjectId.get());
  }
}
