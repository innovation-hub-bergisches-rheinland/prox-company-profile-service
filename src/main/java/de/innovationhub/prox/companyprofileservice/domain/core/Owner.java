package de.innovationhub.prox.companyprofileservice.domain.core;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "owner", uniqueConstraints = @UniqueConstraint(
    columnNames = {"owner_id", "owner_type"}
))
public class Owner {

  @Id
  @Column(name = "owner_id")
  private UUID ownerId;

  @Enumerated(EnumType.ORDINAL)
  @Column(name = "owner_type", nullable = false)
  private OwnerType ownerType;
}
