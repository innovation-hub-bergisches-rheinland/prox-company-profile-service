package de.innovationhub.prox.companyprofileservice.domain.entities.language;

import de.innovationhub.prox.companyprofileservice.domain.entities.core.AbstractEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Length;

/** Languages based on ISO 639-2 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
@NoArgsConstructor
@Entity
public class Language extends AbstractEntity {
  @NaturalId
  @NotBlank
  @Length(min = 2, max = 2)
  private String isoIdentifier2;

  @NotBlank
  @Size(max = 255)
  private String englishName;

  @NotBlank
  @Size(max = 255)
  private String germanName;

  // ISO 3166-1-alpha-2 mapping
  @Column(name = "iso3166_mapping")
  @Length(min = 2, max = 2)
  private String iso3166Mapping;

  @NotNull private Type type;
}
