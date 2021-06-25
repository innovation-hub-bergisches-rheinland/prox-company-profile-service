package de.innovationhub.prox.companyprofileservice.application.security;

import de.innovationhub.prox.companyprofileservice.domain.company.Company;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyRepository;
import java.io.Serializable;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserIsOwnerOfCompanyPermissionEvaluator implements PermissionEvaluator {

  private final CompanyRepository companyRepository;
  private final KeycloakAuthenticationService keycloakAuthenticationService;

  @Autowired
  public UserIsOwnerOfCompanyPermissionEvaluator(CompanyRepository companyRepository, KeycloakAuthenticationService keycloakAuthenticationService) {
    this.companyRepository = companyRepository;
    this.keycloakAuthenticationService = keycloakAuthenticationService;
  }

  @Override
  public boolean hasPermission(Authentication authentication, Object targetDomainObject,
      Object permission) {
    if(authentication == null || targetDomainObject == null || !(permission instanceof String)) {
      return false;
    }

    log.info("Evaluating permission '" + permission + "' on " + targetDomainObject);

    var strPermission = (String) permission;

    if(targetDomainObject instanceof Company) {
      if(strPermission.equalsIgnoreCase("OWNER")) {
        var company = (Company) targetDomainObject;
        return keycloakAuthenticationService.getSubjectId(authentication)
            .map(id -> {
              log.info("Subject ID is '" + id + "' and creator ID is '" + company.getCreatorId() + "'");
              return company.getCreatorId().equals(id);
            })
            .orElse(false);
      }
    }

    return false;
  }

  @Override
  public boolean hasPermission(Authentication authentication, Serializable targetId,
      String targetType, Object permission) {
    if(!(targetId instanceof UUID || targetId instanceof String) || !targetType.equalsIgnoreCase("company")) {
      return false;
    }

    log.info("Evaluating permission '" + permission + "' on " + targetId);

    UUID companyId;
    if(targetId instanceof String) {
      companyId = UUID.fromString((String) targetId);
    } else {
      companyId = (UUID) targetId;
    }
    var optCreatorId = this.companyRepository.getOwnerIdOfCompany(companyId);

    return optCreatorId.map(uuid -> keycloakAuthenticationService.getSubjectId(authentication)
        .map(id -> {
          log.info("Subject ID is '" + id + "' and creator ID is '" + uuid + "'");
          return optCreatorId.get().equals(id);
        })
        .orElse(false)).orElse(false);
  }
}
