package de.innovationhub.prox.companyprofileservice.application.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import de.innovationhub.prox.companyprofileservice.application.controller.company.language.LanguageController;
import de.innovationhub.prox.companyprofileservice.domain.company.language.Language;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class LanguageRepresentationModelAssembler
    implements SimpleRepresentationModelAssembler<Language> {

  @Override
  public void addLinks(EntityModel<Language> resource) {
    var language = resource.getContent();
    if (language != null) {
      resource.add(
          linkTo(methodOn(LanguageController.class).getLanguage(language.getId())).withSelfRel());
    }
  }

  @Override
  public void addLinks(CollectionModel<EntityModel<Language>> resources) {
    resources.add(linkTo(methodOn(LanguageController.class).getAllLanguages(null)).withSelfRel());
  }
}
