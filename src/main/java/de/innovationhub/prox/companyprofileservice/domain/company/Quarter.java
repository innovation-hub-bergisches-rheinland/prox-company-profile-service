package de.innovationhub.prox.companyprofileservice.domain.company;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quarter {
  @NotBlank
  private String location;

  public Quarter(String location) {
    if(location == null || location.isBlank()) {
      throw new IllegalArgumentException("Location cannot be null or blank");
    }
    this.location = location;
  }
}
