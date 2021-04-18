package de.innovationhub.prox.companyprofileservice.application.service.company;

import de.innovationhub.prox.companyprofileservice.application.exception.company.CompanyNotFoundException;
import de.innovationhub.prox.companyprofileservice.application.service.language.LanguageService;
import de.innovationhub.prox.companyprofileservice.domain.company.Company;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyRepository;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

  private final CompanyRepository companyRepository;
  private final LanguageService languageService;

  @Autowired
  public CompanyServiceImpl(CompanyRepository companyRepository, LanguageService languageService) {
    this.companyRepository = companyRepository;
    this.languageService = languageService;
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

  @Override
  public Company setCompanyLanguages(UUID id, Iterable<UUID> languageIds) {
    return this.getCompanyById(id).map(company -> {
      var languages = StreamSupport
          .stream(languageService.getLanguagesWithIds(languageIds).spliterator(), false).collect(
              Collectors.toSet());
      company.setLanguages(languages);
      return this.saveCompany(company);
    }).orElseThrow(CompanyNotFoundException::new);
  }
}
