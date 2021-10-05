package de.innovationhub.prox.companyprofileservice.application.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.OidcKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KeycloakAuthenticationService {

  public Set<UUID> getGroupIds(Authentication authentication) {
    return this.toAccessToken(authentication)
        .map(token -> token.getOtherClaims().get("group_ids"))
        .map(groupIds -> {
          Stream<UUID> uuidStream = Stream.<UUID>builder().build();
          if(groupIds instanceof Collection) {
            var collection = (Collection<String>) groupIds;
            uuidStream = collection.stream().map(UUID::fromString);
          }
          else if(groupIds instanceof String[]) {
            uuidStream = Arrays.stream((String[]) groupIds).map(UUID::fromString);
          } else {
            throw new RuntimeException("Unsupported type");
          }
          return uuidStream.collect(Collectors.toSet());
        })
        .orElse(Collections.emptySet());
  }

  private Optional<AccessToken> toAccessToken(Authentication authentication) {
    return Optional.ofNullable(authentication)
        .map(KeycloakAuthenticationToken.class::cast)
        .map(KeycloakAuthenticationToken::getAccount)
        .map(OidcKeycloakAccount::getKeycloakSecurityContext)
        .map(KeycloakSecurityContext::getToken);
  }
}
