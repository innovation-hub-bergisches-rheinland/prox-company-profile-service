package de.innovationhub.prox.companyprofileservice.application.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@EnableHypermediaSupport(type = HypermediaType.HAL)
public class WebConfig implements WebMvcConfigurer {}
