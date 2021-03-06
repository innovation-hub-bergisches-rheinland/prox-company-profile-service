package de.innovationhub.prox.companyprofileservice.application.config;


import de.innovationhub.prox.companyprofileservice.application.security.UserIsOwnerOfCompanyPermissionEvaluator;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@KeycloakConfiguration
public class KeycloakConfig extends KeycloakWebSecurityConfigurerAdapter {

  private final UserIsOwnerOfCompanyPermissionEvaluator userIsOwnerOfCompanyPermissionEvaluator;

  @Autowired
  public KeycloakConfig(
      UserIsOwnerOfCompanyPermissionEvaluator userIsOwnerOfCompanyPermissionEvaluator) {
    this.userIsOwnerOfCompanyPermissionEvaluator = userIsOwnerOfCompanyPermissionEvaluator;
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
  public void configure(WebSecurity web) throws Exception {
    var handler = new DefaultWebSecurityExpressionHandler();
    handler.setPermissionEvaluator(userIsOwnerOfCompanyPermissionEvaluator);
    web.expressionHandler(handler);
  }

  // TODO: Use actual hasPermission() expression instead of bean invocation
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    super.configure(http);
    http.cors()
        .and()
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, "/**")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/companies/**")
        .access("hasRole('company-manager')")
        .antMatchers(HttpMethod.POST, "/companies/{id}/logo/**")
        .access(
            "hasRole('company-manager') and @userIsOwnerOfCompanyPermissionEvaluator.hasPermission(authentication, #id, 'company', 'OWNER')")
        .antMatchers(HttpMethod.PUT, "/companies/{id}/**", "/companies/{id}/languages/**")
        .access(
            "hasRole('company-manager') and @userIsOwnerOfCompanyPermissionEvaluator.hasPermission(authentication, #id, 'company', 'OWNER')")
        .antMatchers(HttpMethod.DELETE, "/companies/{id}/**", "companies/{id}/logo/**")
        .access(
            "hasRole('company-manager') and @userIsOwnerOfCompanyPermissionEvaluator.hasPermission(authentication, #id, 'company', 'OWNER')")
        .anyRequest()
        .denyAll();
  }
}
