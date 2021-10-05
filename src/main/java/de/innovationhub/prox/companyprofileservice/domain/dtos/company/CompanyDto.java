package de.innovationhub.prox.companyprofileservice.domain.dtos.company;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.innovationhub.prox.companyprofileservice.application.validation.language.AllValidLanguageIsoIdentifiers;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanyDto {
  @NotBlank
  @Size(max = 255)
  @JsonProperty("name")
  private String name;

  @Size(max = 255)
  @JsonProperty("founding_date")
  private String foundingDate;

  @Size(max = 255)
  @JsonProperty("number_of_employees")
  private String numberOfEmployees;

  @Size(max = 255)
  @JsonProperty("homepage")
  private String homepage;

  @Email
  @Size(max = 255)
  @JsonProperty("contact_email")
  private String contactEmail;

  @JsonProperty("vita")
  private String vita;

  @JsonProperty("headquarter")
  private String headquarter;

  @JsonProperty("quarters")
  private Set<String> quarters;

  @JsonProperty("branches")
  private Set<String> branches;

  @AllValidLanguageIsoIdentifiers
  @JsonProperty("languages")
  private Set<String> languages;

  @JsonProperty("social")
  private SocialMediaDto social;
}
