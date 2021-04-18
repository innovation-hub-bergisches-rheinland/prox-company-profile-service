package de.innovationhub.prox.companyprofileservice.domain.company;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.innovationhub.prox.companyprofileservice.domain.core.AbstractEntity;
import de.innovationhub.prox.companyprofileservice.domain.language.Language;
import java.util.List;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company extends AbstractEntity {

  @Embedded @NotNull private CompanyInformation information;

  @Embedded private Quarter headquarter;

  @ElementCollection private Set<Quarter> quarters;

  @ManyToMany @JsonIgnore private Set<Language> languages;

  @ElementCollection private Set<Branch> branches;

  public Company(
      CompanyInformation information,
      Quarter headquarter,
      Set<Quarter> quarters,
      Set<Language> languages,
      Set<Branch> branches) {
    if (information == null) {
      throw new IllegalArgumentException("Company Information cannot be null");
    }
    this.information = information;
    this.headquarter = headquarter;
    this.quarters = quarters;
    this.languages = languages;
    this.branches = branches;
  }
}
