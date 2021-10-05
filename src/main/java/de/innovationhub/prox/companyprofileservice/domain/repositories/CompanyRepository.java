package de.innovationhub.prox.companyprofileservice.domain.repositories;

import de.innovationhub.prox.companyprofileservice.domain.entities.company.Company;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CompanyRepository extends CrudRepository<Company, UUID> {
  Optional<Company> findByOwnerId(UUID uuid);
}
