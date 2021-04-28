package de.innovationhub.prox.companyprofileservice.application.config;

import de.innovationhub.prox.companyprofileservice.application.security.KeycloakAuditorAware;
import de.innovationhub.prox.companyprofileservice.application.security.KeycloakAuthenticationService;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class PersistenceConfig {

  @Bean
  AuditorAware<UUID> auditorProvider(KeycloakAuthenticationService keycloakAuthenticationService) {
    return new KeycloakAuditorAware(keycloakAuthenticationService);
  }
}
