package de.innovationhub.prox.companyprofileservice.domain.company;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.innovationhub.prox.companyprofileservice.domain.company.language.Language;
import de.innovationhub.prox.companyprofileservice.domain.company.quarter.Quarter;
import de.innovationhub.prox.companyprofileservice.domain.core.AbstractEntity;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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

  @ManyToOne @JsonIgnore private Quarter headquarter;

  @ManyToMany(fetch = FetchType.LAZY)
  @JsonIgnore
  private Set<Quarter> quarters;

  @ManyToMany(fetch = FetchType.LAZY)
  @JsonIgnore
  private Set<Language> languages;

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

  public void setInformation(CompanyInformation information) {
    if (information == null) {
      throw new IllegalArgumentException("Company Information cannot be null");
    }
    this.information = information;
  }
}
