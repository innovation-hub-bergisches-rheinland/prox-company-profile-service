package de.innovationhub.prox.companyprofileservice.domain.company;

import javax.persistence.Embeddable;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyInformation {

  @NotBlank private String name;

  private String foundingDate;

  // String because inputs like '100-200', 'about 200' or '10 (DE), 20 (International)' are possible
  private String numberOfEmployees;

  private String homepage;

  @Lob
  private String vita;

  public CompanyInformation(
      String name, String foundingDate, String numberOfEmployees, String homepage, String vita) {
    this.setName(name);
    this.setFoundingDate(foundingDate);
    this.setNumberOfEmployees(numberOfEmployees);
    this.setHomepage(homepage);
    this.setVita(vita);
  }

  public CompanyInformation(String name) {
    this.setName(name);
  }

  public void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name of company cannot be null or empty");
    }
    this.name = name;
  }
}
