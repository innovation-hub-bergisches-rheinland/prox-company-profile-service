package de.innovationhub.prox.companyprofileservice.domain.company.location;

import de.innovationhub.prox.companyprofileservice.domain.company.language.Type;
import de.innovationhub.prox.companyprofileservice.domain.core.AbstractEntity;
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

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Country extends AbstractEntity {

  @NaturalId
  @NotBlank
  @Length(min = 2, max = 2)
  private String isoIdentifier2;

  @NotBlank
  private String englishName;

  @NotBlank
  private String germanName;

  public Country(String isoIdentifier2, String englishName, String germanName) {
     this.setIsoIdentifier2(isoIdentifier2);
     this.setEnglishName(englishName);
     this.setGermanName(germanName);
  }

  public void setGermanName(String germanName) {
    if(germanName == null || germanName.isBlank()) {
      throw new IllegalArgumentException("german name cannot be null or blank");
    }
    this.germanName = germanName;
  }

  public void setEnglishName(String englishName) {
    if(englishName == null || englishName.isBlank()) {
      throw new IllegalArgumentException("english name cannot be null or blank");
    }
    this.englishName = englishName;
  }

  public void setIsoIdentifier2(String isoIdentifier2) {
    if(isoIdentifier2 == null || isoIdentifier2.isBlank()) {
      throw new IllegalArgumentException("iso identifier cannot be null or blank");
    }
    if(isoIdentifier2.trim().toUpperCase().length() != 2) {
      throw new IllegalArgumentException("iso identifier must have 2 letters");
    }
    this.isoIdentifier2 = isoIdentifier2.trim().toUpperCase();
  }
}