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
}
