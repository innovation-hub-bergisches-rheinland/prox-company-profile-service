package de.innovationhub.prox.companyprofileservice.domain.company;


import de.innovationhub.prox.companyprofileservice.domain.core.AbstractEntity;
import java.util.UUID;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
@Entity
@AllArgsConstructor
public class CompanyLogo extends AbstractEntity {
  @ContentId private UUID contentId;

  @ContentLength private Long contentLength;

  private String mimeType;
}
