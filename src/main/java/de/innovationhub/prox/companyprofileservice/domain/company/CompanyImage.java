package de.innovationhub.prox.companyprofileservice.domain.company;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Embeddable
public class CompanyImage {
  @NotBlank
  private String filename;

  public CompanyImage(String filename) {
    if(filename == null || filename.isBlank()) {
      throw new IllegalArgumentException("Filename cannot be null or empty");
    }
    this.filename = filename;
  }
}