package de.innovationhub.prox.companyprofileservice.domain.company;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;
import org.springframework.content.commons.repository.ContentStore;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyLogoStore extends ContentStore<CompanyLogo, UUID> {

}
