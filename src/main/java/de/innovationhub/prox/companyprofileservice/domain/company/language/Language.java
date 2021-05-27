package de.innovationhub.prox.companyprofileservice.domain.company.language;

import de.innovationhub.prox.companyprofileservice.domain.core.AbstractEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Language extends AbstractEntity {
  @NaturalId
  @NotBlank
  @Length(min = 2, max = 2)
  private String isoIdentifier2;

  @NotBlank private String englishName;

  @NotBlank private String germanName;

  // ISO 3166-1-alpha-2 mapping
  @Column(name = "iso3166_mapping")
  @Length(min = 2, max = 2)
  private String iso3166Mapping;

  @NotNull private Type type;

  public Language(
      String isoIdentifier2,
      String englishName,
      String germanName,
      Type type,
      String iso3166Mapping) {
    if (isoIdentifier2 == null || isoIdentifier2.length() != 2 && !isoIdentifier2.isBlank()) {
      throw new IllegalArgumentException("ISO 639-2 Identifier invalid");
    }
    if (iso3166Mapping != null && iso3166Mapping.length() != 2) {
      throw new IllegalArgumentException("ISO 3166-1 Alpha 2 mapping must contain exactly 2 chars");
    }
    if (englishName == null
        || englishName.isBlank()
        || germanName == null
        || germanName.isBlank()) {
      throw new IllegalArgumentException("Language name cannot be blank or null");
    }
    if (type == null) {
      throw new IllegalArgumentException("Language type cannot be null use Type.NONE instead");
    }
    this.isoIdentifier2 = isoIdentifier2.toLowerCase();
    this.englishName = englishName;
    this.germanName = germanName;
    this.type = type;
    this.iso3166Mapping = iso3166Mapping;
  }

  public void setIsoIdentifier2(String isoIdentifier2) {
    if (isoIdentifier2 == null || isoIdentifier2.length() != 2 && !isoIdentifier2.isBlank()) {
      throw new IllegalArgumentException("ISO 639-2 Identifier invalid");
    }
    this.isoIdentifier2 = isoIdentifier2.toLowerCase();
  }

  public void setEnglishName(String englishName) {
    if (englishName == null || englishName.isBlank()) {
      throw new IllegalArgumentException("Language name cannot be blank or null");
    }
    this.englishName = englishName;
  }

  public void setGermanName(String germanName) {
    if (germanName == null || germanName.isBlank()) {
      throw new IllegalArgumentException("Language name cannot be blank or null");
    }
    this.germanName = germanName;
  }

  public void setType(Type type) {
    if (type == null) {
      throw new IllegalArgumentException("Language type cannot be null use Type.NONE instead");
    }
    this.type = type;
  }
}
