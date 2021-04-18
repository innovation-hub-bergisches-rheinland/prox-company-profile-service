package de.innovationhub.prox.companyprofileservice.domain.company;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@ToString
public class Branch {

  @NotBlank private String branchName;

  public Branch(String branchName) {
    if (branchName == null || branchName.isBlank()) {
      throw new IllegalArgumentException("Branch name cannot be null or empty");
    }
    this.branchName = branchName;
  }
}
