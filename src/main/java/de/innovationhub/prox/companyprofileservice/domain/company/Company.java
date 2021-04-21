package de.innovationhub.prox.companyprofileservice.domain.company;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.innovationhub.prox.companyprofileservice.domain.company.language.Language;
import de.innovationhub.prox.companyprofileservice.domain.company.quarter.Quarter;
import de.innovationhub.prox.companyprofileservice.domain.core.AbstractEntity;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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

  @OneToOne @JsonIgnore private CompanyLogo companyLogo;

  @ElementCollection private Set<Branch> branches;

  public Company(
      CompanyInformation information,
      Quarter headquarter,
      Set<Quarter> quarters,
      Set<Language> languages,
      Set<Branch> branches) {
    this.setInformation(information);
    this.setHeadquarter(headquarter);
    this.setQuarters(quarters);
    this.setLanguages(languages);
    this.setBranches(branches);
  }

  public Company(CompanyInformation information) {
    this.setInformation(information);
  }

  public void setInformation(CompanyInformation information) {
    if (information == null) {
      throw new IllegalArgumentException("Company Information cannot be null");
    }
    this.information = information;
  }
}
