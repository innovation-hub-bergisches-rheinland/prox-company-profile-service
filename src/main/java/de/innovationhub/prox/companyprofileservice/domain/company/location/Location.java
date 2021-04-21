package de.innovationhub.prox.companyprofileservice.domain.company.location;

import de.innovationhub.prox.companyprofileservice.domain.core.AbstractEntity;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Length;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Location extends AbstractEntity {

  @NaturalId
  @NotNull
  @Length(min = 3, max = 3)
  private String locode;

  @NotNull
  @NotBlank
  private String name;

  @NotNull
  @NotBlank
  private String nameWithoutDiacritics;

  @NotNull
  @NotBlank
  private String coordiantes;

  @ManyToOne(optional = false)
  private Country country;

  public Location(String locode, String name, String nameWithoutDiacritics, String coordiantes, Country country) {
    this.setLocode(locode);
    this.setName(name);
    this.setNameWithoutDiacritics(nameWithoutDiacritics);
    this.setCoordiantes(coordiantes);
    this.setCountry(country);
  }

  public void setLocode(String locode) {
    if(locode == null || locode.isBlank()) {
      throw new IllegalArgumentException("locode cannot be null or blank");
    }
    if(locode.trim().toUpperCase().length() != 3) {
      throw new IllegalArgumentException("locode must have 3 letters");
    }
    this.locode = locode.trim().toUpperCase();
  }

  public void setName(String name) {
    if(name == null || name.isBlank()) {
      throw new IllegalArgumentException("name cannot be null or blank");
    }
    this.name = name;
  }

  public void setNameWithoutDiacritics(String nameWithoutDiacritics) {
    if(nameWithoutDiacritics == null || nameWithoutDiacritics.isBlank()) {
      throw new IllegalArgumentException("nameWithoutDiacritics cannot be null or blank");
    }
    this.nameWithoutDiacritics = nameWithoutDiacritics;
  }

  public void setCoordiantes(String coordiantes) {
    if(coordiantes == null || coordiantes.isBlank()) {
      throw new IllegalArgumentException("coordiantes cannot be null or blank");
    }
    this.coordiantes = coordiantes;
  }

  public void setCountry(
      Country country) {
    if(country == null) {
      throw new IllegalArgumentException("country cannot be null");
    }
    this.country = country;
  }
}
