package de.innovationhub.prox.companyprofileservice.application.config;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@KeycloakConfiguration
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

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
    http.csrf()
        .disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, "/**")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/companies")
        .access("hasRole('company-manager') and @securityService.authenticatedUserIsNotOwnerOfAnyCompany()")
        .antMatchers(HttpMethod.POST, "/companies/{id}/logo")
        .access("hasRole('company-manager') and @securityService.authenticatedUserIsOwnerOfCompany(#id)")
        .antMatchers(HttpMethod.PUT, "/companies/{id}", "/companies/{id}/languages")
        .access("hasRole('company-manager') and @securityService.authenticatedUserIsOwnerOfCompany(#id)")
        .antMatchers(HttpMethod.DELETE, "/companies/{id}", "companies/{id}/logo")
        .access("hasRole('company-manager') and @securityService.authenticatedUserIsOwnerOfCompany(#id)")
        .anyRequest()
        .permitAll();
  }
}
