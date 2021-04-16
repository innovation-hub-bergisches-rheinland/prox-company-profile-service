package de.innovationhub.prox.companyprofileservice.domain.company;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyInformation {

  @NotBlank
  private String name;

  private String foundingDate;

  //String because inputs like '100-200', 'about 200' or '10 (DE), 20 (International)' are possible
  private String numberOfEmployees;

  private String homepage;

  private String vita;

  public CompanyInformation(String name, String foundingDate, String numberOfEmployees, String homepage, String vita) {
    if(name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name of company cannot be null or empty");
    }
    this.name = name;
    this.foundingDate = foundingDate;
    this.numberOfEmployees= numberOfEmployees;
    this.homepage = homepage;
    this.vita = vita;
  }
}
