package de.innovationhub.prox.companyprofileservice.domain.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.UUID;
import javax.persistence.Column;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

public class Audit {
  @CreatedBy
  @JsonIgnore
  @Column(unique = true)
  private UUID createdBy;

  @CreatedDate
  @JsonIgnore
  @Column(unique = true)
  private LocalDate createdDate;

  @LastModifiedBy
  @JsonIgnore
  @Column(unique = true)
  private UUID lastModifiedBy;

  @LastModifiedDate
  @JsonIgnore
  @Column(unique = true)
  private LocalDate lastModifiedDate;
}
