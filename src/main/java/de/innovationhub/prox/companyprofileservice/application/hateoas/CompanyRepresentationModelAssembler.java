package de.innovationhub.prox.companyprofileservice.application.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import de.innovationhub.prox.companyprofileservice.application.controller.company.CompanyController;
import de.innovationhub.prox.companyprofileservice.application.controller.company.CompanyLogoController;
import de.innovationhub.prox.companyprofileservice.domain.company.Company;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class CompanyRepresentationModelAssembler
    implements SimpleRepresentationModelAssembler<Company> {

  @Override
  public void addLinks(EntityModel<Company> resource) {
    var company = resource.getContent();
    if (company != null) {
      resource.add(
          linkTo(methodOn(CompanyController.class).getCompany(company.getId())).withSelfRel());
      resource.add(
          linkTo(methodOn(CompanyController.class).getCompanyLanguages(company.getId()))
              .withRel("languages"));
      resource.add(
          linkTo(methodOn(CompanyLogoController.class).getCompanyLogo(company.getId())).withRel("image"));
    }
  }

  @Override
  public void addLinks(CollectionModel<EntityModel<Company>> resources) {
    resources.add(linkTo(methodOn(CompanyController.class).getAllCompanies()).withSelfRel());
  }
}
