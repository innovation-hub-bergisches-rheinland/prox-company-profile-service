package de.innovationhub.prox.companyprofileservice.domain.company;


import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CompanyRepository extends CrudRepository<Company, UUID> {
  boolean existsByCreatorId(UUID uuid);

  @Query("select c.creatorId from Company c where c.id = ?1")
  Optional<UUID> getOwnerIdOfCompany(UUID uuid);

  Optional<Company> findCompanyByCreatorId(UUID creatorId);
}
