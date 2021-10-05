package de.innovationhub.prox.companyprofileservice.application.config;

import de.innovationhub.prox.companyprofileservice.application.security.OrganizationGuard;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
@KeycloakConfiguration
public class KeycloakConfig extends KeycloakWebSecurityConfigurerAdapter {

  private final OrganizationGuard organizationGuard;

  @Autowired
  public KeycloakConfig(
      OrganizationGuard organizationGuard) {
    this.organizationGuard = organizationGuard;
  }

  @Bean
  public KeycloakSpringBootConfigResolver keycloakConfigResolver() {
    return new KeycloakSpringBootConfigResolver();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) {
    KeycloakAuthenticationProvider keycloakAuthenticationProvider =
        this.keycloakAuthenticationProvider();
    keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
    auth.authenticationProvider(keycloakAuthenticationProvider);
  }

  @Bean
  @Override
  protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
    return new NullAuthenticatedSessionStrategy();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    super.configure(http);
    http.cors()
        .and()
        .csrf()
        .disable()
        .authorizeRequests()
        .mvcMatchers(HttpMethod.GET, "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**")
        .permitAll()
        .mvcMatchers(HttpMethod.GET, "/languages")
        .permitAll()
        .mvcMatchers(HttpMethod.GET, "/orgs/{id}/profile/**")
        .permitAll()
        .mvcMatchers(HttpMethod.POST, "/orgs/{id}/profile/**")
        .access("hasRole('company-manager') and @organizationGuard.checkOrgId(authentication, #id)")
        .mvcMatchers(HttpMethod.PUT, "/orgs/{id}/profile/**")
        .access("hasRole('company-manager') and @organizationGuard.checkOrgId(authentication, #id)")
        .mvcMatchers(HttpMethod.DELETE, "/orgs/{id}/profile/**")
        .access("hasRole('company-manager') and @organizationGuard.checkOrgId(authentication, #id)")
        .anyRequest()
        .denyAll();
  }
}
