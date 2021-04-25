package de.innovationhub.prox.companyprofileservice.application.service.company;

import de.innovationhub.prox.companyprofileservice.application.exception.core.CustomEntityNotFoundException;
import de.innovationhub.prox.companyprofileservice.application.service.company.language.LanguageService;
import de.innovationhub.prox.companyprofileservice.domain.company.Company;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyRepository;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

  private final CompanyRepository companyRepository;
  private final CompanyLogoService companyLogoService;
  private final LanguageService languageService;
  private final ModelMapper modelMapper;

  @Autowired
  public CompanyServiceImpl(
      CompanyRepository companyRepository,
      @Lazy CompanyLogoService companyLogoService,
      LanguageService languageService) {
    this.companyRepository = companyRepository;
    this.companyLogoService = companyLogoService;
    this.languageService = languageService;
    this.modelMapper = new ModelMapper();
  }

  @Override
  public Iterable<Company> getAll() {
    return this.companyRepository.findAll();
  }

  @Override
  public Optional<Company> getById(UUID id) {
    return this.companyRepository.findById(id);
  }

  @Override
  public Company save(Company company) {
    return this.companyRepository.save(company);
  }

  @Override
  public Company update(UUID id, Company company) {
    return this.getById(id)
        .map(
            c -> {
              // TODO: use ModelMapper modelMapper.map(company, c);
              c.setHeadquarter(company.getHeadquarter());
              c.setQuarters(company.getQuarters());
              c.setLanguages(company.getLanguages());
              c.setBranches(company.getBranches());
              c.setInformation(company.getInformation());
              return c;
            })
        .map(companyRepository::save)
        .orElseThrow(() -> new CustomEntityNotFoundException("Company with id " + id.toString() + " not found"));
  }

  @Override
  public void deleteById(UUID id) {
    this.getById(id).ifPresentOrElse(
        c -> {
          this.companyLogoService.deleteCompanyLogo(c.getId());
          this.companyRepository.deleteById(id);
        }, () -> {
          throw new CustomEntityNotFoundException("Company with id " + id.toString() + " not found");
        }
    );
  }

  @Override
  public Company setCompanyLanguages(UUID id, Iterable<UUID> languageIds) {
    return this.getById(id)
        .map(
            company -> {
              var languages =
                  StreamSupport.stream(
                          languageService.getLanguagesWithIds(languageIds).spliterator(), false)
                      .collect(Collectors.toSet());
              company.setLanguages(languages);
              return this.save(company);
            })
        .orElseThrow(() -> new CustomEntityNotFoundException("Company with id " + id.toString() + " not found"));
  }
}
