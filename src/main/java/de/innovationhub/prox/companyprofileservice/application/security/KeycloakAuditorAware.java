package de.innovationhub.prox.companyprofileservice.application.security;

import com.netflix.discovery.converters.Auto;
import java.util.Optional;
import java.util.UUID;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.OidcKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/** AuditorAware implementation for a Keycloak managed authentication */
@Component
public class KeycloakAuditorAware implements AuditorAware<UUID> {

  private final KeycloakAuthenticationService keycloakAuthenticationService;

  @Autowired
  public KeycloakAuditorAware(KeycloakAuthenticationService keycloakAuthenticationService) {
    this.keycloakAuthenticationService = keycloakAuthenticationService;
  }

  @Override
  public Optional<UUID> getCurrentAuditor() {
    return this.keycloakAuthenticationService.getSubjectId();
  }
}
