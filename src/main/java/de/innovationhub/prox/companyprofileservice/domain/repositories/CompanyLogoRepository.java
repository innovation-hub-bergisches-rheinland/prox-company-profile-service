package de.innovationhub.prox.companyprofileservice.domain.repositories;

import de.innovationhub.prox.companyprofileservice.domain.entities.company.CompanyLogo;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyLogoRepository extends CrudRepository<CompanyLogo, UUID> {}
