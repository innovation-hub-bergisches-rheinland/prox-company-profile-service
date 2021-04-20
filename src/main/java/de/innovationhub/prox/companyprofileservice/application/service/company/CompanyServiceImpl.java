package de.innovationhub.prox.companyprofileservice.application.service.company;

import de.innovationhub.prox.companyprofileservice.application.exception.company.CompanyNotFoundException;
import de.innovationhub.prox.companyprofileservice.application.exception.core.CustomEntityNotFoundException;
import de.innovationhub.prox.companyprofileservice.application.service.company.language.LanguageService;
import de.innovationhub.prox.companyprofileservice.application.service.company.quarter.QuarterService;
import de.innovationhub.prox.companyprofileservice.domain.company.Company;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyLogo;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyRepository;
import de.innovationhub.prox.companyprofileservice.domain.company.quarter.Quarter;
import java.io.IOException;
import java.io.InputStream;
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
  private final QuarterService quarterService;
  private final ModelMapper modelMapper;

  @Autowired
  public CompanyServiceImpl(
      CompanyRepository companyRepository,
      @Lazy CompanyLogoService companyLogoService,
      LanguageService languageService,
      QuarterService quarterService) {
    this.companyRepository = companyRepository;
    this.companyLogoService = companyLogoService;
    this.languageService = languageService;
    this.quarterService = quarterService;
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
        .orElseThrow(CompanyNotFoundException::new);
  }

  @Override
  public void deleteById(UUID id) {
    this.getById(id).ifPresentOrElse(
        c -> {
          this.companyLogoService.deleteCompanyLogo(c.getId());
          this.companyRepository.deleteById(id);
        }, () -> {
          throw new CompanyNotFoundException();
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
        .orElseThrow(CompanyNotFoundException::new);
  }

  @Override
  public Iterable<Quarter> getCompanyQuarters(UUID id) {
    return this.getById(id).map(Company::getQuarters).orElseThrow(CompanyNotFoundException::new);
  }

  @Override
  public Iterable<Quarter> setCompanyQuarters(UUID id, Iterable<UUID> quarterIds) {
    return this.getById(id)
        .map(
            company -> {
              var quarters =
                  StreamSupport.stream(quarterIds.spliterator(), false)
                      .map(quarterService::getById)
                      .filter(Optional::isPresent)
                      .map(Optional::get)
                      .collect(Collectors.toSet());
              company.setQuarters(quarters);
              this.save(company);
              return quarters;
            })
        .orElseThrow(CompanyNotFoundException::new);
  }

  @Override
  public Optional<Quarter> getCompanyHeadquarter(UUID id) {
    return this.getById(id).map(Company::getHeadquarter);
  }

  @Override
  public Quarter setCompanyHeadquarter(UUID id, UUID quarterId) {
    return this.getById(id)
        .map(
            company -> {
              return this.quarterService
                  .getById(quarterId)
                  .map(
                      quarter -> {
                        company.setHeadquarter(quarter);
                        this.save(company);
                        return quarter;
                      })
                  .orElseThrow(
                      () ->
                          new CustomEntityNotFoundException(
                              "Quarter with id " + quarterId + " not found"));
            })
        .orElseThrow(CompanyNotFoundException::new);
  }
}
