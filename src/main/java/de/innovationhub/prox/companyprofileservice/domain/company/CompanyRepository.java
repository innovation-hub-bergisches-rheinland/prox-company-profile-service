package de.innovationhub.prox.companyprofileservice.domain.company;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends CrudRepository<Company, UUID> {
  boolean existsByCreatorId(UUID uuid);

  Optional<Company> findCompanyByCreatorId(UUID creatorId);
}
