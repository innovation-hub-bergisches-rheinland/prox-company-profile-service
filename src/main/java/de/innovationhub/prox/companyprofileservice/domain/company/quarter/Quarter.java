package de.innovationhub.prox.companyprofileservice.domain.company.quarter;

import de.innovationhub.prox.companyprofileservice.domain.core.AbstractEntity;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quarter extends AbstractEntity {
  @NotBlank private String location;

  public Quarter(String location) {
    if (location == null || location.isBlank()) {
      throw new IllegalArgumentException("Location cannot be null or blank");
    }
    this.location = location;
  }

  public void setLocation(String location) {
    if (location == null || location.isBlank()) {
      throw new IllegalArgumentException("Location cannot be null or blank");
    }
    this.location = location;
  }
}