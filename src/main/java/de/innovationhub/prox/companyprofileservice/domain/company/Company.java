package de.innovationhub.prox.companyprofileservice.domain.company;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import de.innovationhub.prox.companyprofileservice.domain.company.language.Language;
import de.innovationhub.prox.companyprofileservice.domain.company.quarter.Quarter;
import de.innovationhub.prox.companyprofileservice.domain.core.AbstractEntity;
import de.innovationhub.prox.companyprofileservice.domain.core.Owner;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company extends AbstractEntity {

  @NotNull
  @OneToOne(optional = false)
  private Owner owner;

  @Embedded @NotNull private CompanyInformation information;

  @Embedded @Valid private Quarter headquarter;

  @ElementCollection
  @JsonInclude(Include.ALWAYS)
  private Set<Quarter> quarters;

  @ManyToMany @JsonIgnore private Set<Language> languages;

  @OneToOne @JsonIgnore private CompanyLogo companyLogo;

  @ElementCollection
  @JsonInclude(Include.ALWAYS)
  private Set<Branch> branches;

  @ElementCollection
  @JsonInclude(Include.ALWAYS)
  private Set<SocialMedia> socialMedia;

  public Company(
      CompanyInformation information,
      Quarter headquarter,
      Set<Quarter> quarters,
      Set<Language> languages,
      Set<Branch> branches,
      Set<SocialMedia> socialMedia) {
    this.setInformation(information);
    this.setHeadquarter(headquarter);
    this.setQuarters(quarters);
    this.setLanguages(languages);
    this.setBranches(branches);
    this.setSocialMedia(socialMedia);
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
