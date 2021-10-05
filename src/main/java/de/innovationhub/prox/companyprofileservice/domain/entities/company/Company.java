package de.innovationhub.prox.companyprofileservice.domain.entities.company;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.innovationhub.prox.companyprofileservice.domain.entities.language.Language;
import de.innovationhub.prox.companyprofileservice.domain.entities.core.AbstractEntity;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Company extends AbstractEntity {

  @NotNull
  @Column(name = "owner_id", unique = true)
  private UUID ownerId;

  @Embedded
  @NotNull
  private CompanyInformation information;

  @Embedded @Valid private Quarter headquarter;

  @ElementCollection
  private Set<Quarter> quarters;

  @ManyToMany
  @JsonIgnore
  private Set<Language> languages;

  @OneToOne
  @JsonIgnore
  private CompanyLogo companyLogo;

  @ElementCollection
  private Set<Branch> branches;

  @Embedded
  private SocialMedia social;
}
