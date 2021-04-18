package de.innovationhub.prox.companyprofileservice.application.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import de.innovationhub.prox.companyprofileservice.application.controller.company.quarter.QuarterController;
import de.innovationhub.prox.companyprofileservice.domain.company.quarter.Quarter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class QuarterRepresentationModelAssembler
    implements SimpleRepresentationModelAssembler<Quarter> {

  @Override
  public void addLinks(EntityModel<Quarter> resource) {
    var quarter = resource.getContent();
    if (quarter != null) {
      resource.add(
          linkTo(methodOn(QuarterController.class).getQuarter(quarter.getId())).withSelfRel());
    }
  }

  @Override
  public void addLinks(CollectionModel<EntityModel<Quarter>> resources) {
    resources.add(linkTo(methodOn(QuarterController.class).getAllQuarters()).withSelfRel());
  }
}
