package de.innovationhub.prox.companyprofileservice.application.security;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class OrganizationGuard {

  private final KeycloakAuthenticationService keycloakAuthenticationService;

  @Autowired
  public OrganizationGuard(
      KeycloakAuthenticationService keycloakAuthenticationService) {
    this.keycloakAuthenticationService = keycloakAuthenticationService;
  }

  public boolean checkOrgId(Authentication authentication, UUID id) {
    return this.keycloakAuthenticationService.getGroupIds(authentication)
        .stream().anyMatch(groupId -> groupId.equals(id));
  }
}
