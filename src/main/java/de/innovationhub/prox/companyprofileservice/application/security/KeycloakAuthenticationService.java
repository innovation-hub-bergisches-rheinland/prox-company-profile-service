package de.innovationhub.prox.companyprofileservice.application.security;


import java.util.Optional;
import java.util.UUID;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.OidcKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class KeycloakAuthenticationService {
  public Optional<UUID> getSubjectId() {
    return Optional.ofNullable(SecurityContextHolder.getContext())
        .map(SecurityContext::getAuthentication)
        .flatMap(this::getSubjectId);
  }

  public Optional<UUID> getSubjectId(Authentication authentication) {
    return Optional.ofNullable(authentication)
        .map(KeycloakAuthenticationToken.class::cast)
        .map(KeycloakAuthenticationToken::getAccount)
        .map(OidcKeycloakAccount::getKeycloakSecurityContext)
        .map(KeycloakSecurityContext::getToken)
        .map(AccessToken::getSubject)
        .map(UUID::fromString);
  }
}
