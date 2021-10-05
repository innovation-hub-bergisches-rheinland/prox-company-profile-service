package de.innovationhub.prox.companyprofileservice.domain.dtos.language;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.innovationhub.prox.companyprofileservice.application.validation.language.AllValidLanguageIsoIdentifiers;
import de.innovationhub.prox.companyprofileservice.domain.dtos.company.SocialMediaDto;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LanguageDto {
  @NotBlank
  @Size(min = 2, max = 2)
  @JsonProperty("iso_identifier")
  private String isoIdentifier;

  @NotBlank
  @Size(max = 255)
  @JsonProperty("german_name")
  private String germanName;

  @NotBlank
  @Size(max = 255)
  @JsonProperty("english_name")
  private String englishName;

  @Size(min = 2, max = 2)
  @JsonProperty("iso3166_mapping")
  private String iso3166Mapping;
}