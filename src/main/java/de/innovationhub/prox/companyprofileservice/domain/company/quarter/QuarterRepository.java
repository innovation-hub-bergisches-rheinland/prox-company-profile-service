package de.innovationhub.prox.companyprofileservice.domain.company.quarter;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuarterRepository extends CrudRepository<Quarter, UUID> {}
