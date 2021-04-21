package de.innovationhub.prox.companyprofileservice.domain.company.location;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends CrudRepository<Country, UUID> {

}
