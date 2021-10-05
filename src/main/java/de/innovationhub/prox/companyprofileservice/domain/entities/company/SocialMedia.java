package de.innovationhub.prox.companyprofileservice.domain.entities.company;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class SocialMedia {

  @Size(max = 255)
  @Pattern(regexp = "^\\s*[-a-zA-Z0-9()_\\+.]*\\s*$")
  private String facebookHandle;

  @Size(max = 255)
  @Pattern(regexp = "^\\s*[-a-zA-Z0-9()_\\+.]*\\s*$")
  private String twitterHandle;

  @Size(max = 255)
  @Pattern(regexp = "^\\s*[-a-zA-Z0-9()_\\+.]*\\s*$")
  private String instagramHandle;

  @Size(max = 255)
  @Pattern(regexp = "^\\s*[-a-zA-Z0-9()_\\+.]*\\s*$")
  private String xingHandle;

  @Size(max = 255)
  @Pattern(regexp = "^\\s*[-a-zA-Z0-9()_\\+.]*\\s*$")
  private String linkedInHandle;
}
