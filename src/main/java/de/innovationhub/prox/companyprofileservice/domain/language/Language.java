package de.innovationhub.prox.companyprofileservice.domain.language;

import de.innovationhub.prox.companyprofileservice.domain.core.AbstractEntity;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Length;

/** Languages based on ISO 639-2 */
@Getter
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

  @NotNull private Type type;

  public Language(String isoIdentifier2, String englishName, String germanName, Type type) {
    if (isoIdentifier2 == null || isoIdentifier2.length() != 2 && !isoIdentifier2.isBlank()) {
      throw new IllegalArgumentException("ISO 639-2 Identifier invalid");
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
  }
}
