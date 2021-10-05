package de.innovationhub.prox.companyprofileservice.domain.entities.company;

import javax.persistence.Embeddable;
import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
public class CompanyInformation {

  @NotBlank
  @Size(max = 255)
  private String name;

  @Size(max = 255)
  private String foundingDate;

  // String because inputs like '100-200', 'about 200' or '10 (DE), 20 (International)' are possible
  @Size(max = 255)
  private String numberOfEmployees;

  @Size(max = 255)
  private String homepage;

  @Size(max = 255)
  @Email
  private String contactEmail;

  @Lob private String vita;
}
