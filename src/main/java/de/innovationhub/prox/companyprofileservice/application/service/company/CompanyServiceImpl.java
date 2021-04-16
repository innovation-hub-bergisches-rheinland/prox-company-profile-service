package de.innovationhub.prox.companyprofileservice.application.service.company;

import de.innovationhub.prox.companyprofileservice.domain.company.Company;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyRepository;
import de.innovationhub.prox.companyprofileservice.domain.language.Language;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

  private final CompanyRepository companyRepository;

  @Autowired
  public CompanyServiceImpl(CompanyRepository companyRepository) {
    this.companyRepository = companyRepository;
  }

  @Override
  public Iterable<Company> getAllCompanies() {
    return this.companyRepository.findAll();
  }

  @Override
  public Optional<Company> getCompanyById(UUID id) {
    return this.companyRepository.findById(id);
  }

  @Override
  public Company saveCompany(Company company) {
    return this.companyRepository.save(company);
  }

  @Override
  public Company updateCompany(Company company) {
    return this.companyRepository.save(company);
  }

  @Override
  public void deleteCompanyById(UUID id) {
    this.companyRepository.deleteById(id);
  }
}
