package de.innovationhub.prox.companyprofileservice.domain.repositories;

import de.innovationhub.prox.companyprofileservice.domain.entities.company.CompanyLogo;
import java.util.UUID;
import org.springframework.content.commons.repository.ContentStore;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyLogoStore extends ContentStore<CompanyLogo, UUID> {}
