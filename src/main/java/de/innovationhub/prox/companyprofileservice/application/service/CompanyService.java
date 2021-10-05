package de.innovationhub.prox.companyprofileservice.application.service;

import de.innovationhub.prox.companyprofileservice.domain.dtos.company.CompanyDto;
import de.innovationhub.prox.companyprofileservice.domain.dtos.company.SocialMediaDto;
import de.innovationhub.prox.companyprofileservice.domain.entities.company.Branch;
import de.innovationhub.prox.companyprofileservice.domain.entities.company.Company;
import de.innovationhub.prox.companyprofileservice.domain.entities.company.CompanyInformation;
import de.innovationhub.prox.companyprofileservice.domain.entities.company.CompanyLogo;
import de.innovationhub.prox.companyprofileservice.domain.entities.company.Quarter;
import de.innovationhub.prox.companyprofileservice.domain.entities.company.SocialMedia;
import de.innovationhub.prox.companyprofileservice.domain.entities.language.Language;
import de.innovationhub.prox.companyprofileservice.domain.repositories.CompanyLogoRepository;
import de.innovationhub.prox.companyprofileservice.domain.repositories.CompanyLogoStore;
import de.innovationhub.prox.companyprofileservice.domain.repositories.CompanyRepository;
import de.innovationhub.prox.companyprofileservice.domain.repositories.language.LanguageRepository;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class CompanyService {

  private final CompanyRepository companyRepository;
  private final CompanyLogoStore companyLogoStore;
  private final CompanyLogoRepository companyLogoRepository;
  private final TikaConfig tikaConfig = TikaConfig.getDefaultConfig();

  private final LanguageService languageService;

  @Autowired
  public CompanyService(
      CompanyRepository companyRepository,
      CompanyLogoStore companyLogoStore,
      CompanyLogoRepository companyLogoRepository,
      LanguageService languageService) {
    this.companyRepository = companyRepository;
    this.companyLogoStore = companyLogoStore;
    this.companyLogoRepository = companyLogoRepository;
    this.languageService = languageService;
  }

  public CompanyDto saveCompany(UUID orgId, @Valid CompanyDto companyDto) {
    var company = this.mapToEntity(companyDto);
    company.setOwnerId(orgId);
    company = companyRepository.save(company);
    return this.mapToDto(company);
  }

  public Optional<CompanyDto> getCompanyOfOrg(UUID orgId) {
    return companyRepository.findByOwnerId(orgId)
        .map(this::mapToDto);
  }

  public Optional<CompanyLogo> getOrganizationLogo(UUID orgId) {
    return companyRepository.findByOwnerId(orgId)
        .map(Company::getCompanyLogo);
  }

  public InputStream getOrganizationLogoAsStream(CompanyLogo companyLogo) {
    return this.companyLogoStore.getContent(companyLogo);
  }

  @Transactional
  public Optional<CompanyLogo> saveOrganizationLogo(UUID orgId, InputStream inputStream) throws IOException {
    var optCompany = this.companyRepository.findByOwnerId(orgId);
    if(optCompany.isPresent()) {
      var company = optCompany.get();
      var companyLogo = company.getCompanyLogo();
      if(companyLogo == null) {
        companyLogo = new CompanyLogo();
      }

      var detector = tikaConfig.getDetector();
      byte[] bytes = inputStream.readAllBytes();
      var tikaInputStream = TikaInputStream.get(new ByteArrayInputStream(bytes));
      var mediaType = detector.detect(tikaInputStream, new Metadata());
      if(!mediaType.getType().equals("image")) {
        throw new RuntimeException("Not an image");
      }
      companyLogo.setMimeType(mediaType.getType() + "/" + mediaType.getSubtype());
      var savedContent = companyLogoStore.setContent(companyLogo, new ByteArrayInputStream(bytes));
      var savedLogo = this.companyLogoRepository.save(savedContent);
      company.setCompanyLogo(savedLogo);
      var savedCompany = this.companyRepository.save(company);
      return Optional.of(savedCompany.getCompanyLogo());
    }
    return Optional.empty();
  }

  @Transactional
  public void deleteOrganizationLogo(UUID orgId) {
    var optCompany = this.companyRepository.findByOwnerId(orgId);
    if(optCompany.isPresent()) {
      var company = optCompany.get();
      var companyLogo = company.getCompanyLogo();
      if(companyLogo != null) {
        var deletedCl = this.companyLogoStore.unsetContent(companyLogo);
        this.companyLogoRepository.deleteById(deletedCl.getId());
        company.setCompanyLogo(null);
        this.companyRepository.save(company);
      }
    }
    throw new RuntimeException("Not Found");
  }



  // TODO: Use automatic mapping, something like ModelMapper or MapStruct, but ensure safety
  private CompanyDto mapToDto(Company company) {
    return new CompanyDto(
        company.getInformation().getName(),
        company.getInformation().getFoundingDate(),
        company.getInformation().getNumberOfEmployees(),
        company.getInformation().getHomepage(),
        company.getInformation().getContactEmail(),
        company.getInformation().getVita(),
        company.getHeadquarter().getLocation(),
        company.getQuarters().stream().map(Quarter::getLocation).collect(Collectors.toSet()),
        company.getBranches().stream().map(Branch::getBranchName).collect(Collectors.toSet()),
        company.getLanguages().stream().map(Language::getIsoIdentifier2).collect(Collectors.toSet()),
        new SocialMediaDto(
            company.getSocial().getFacebookHandle(),
            company.getSocial().getTwitterHandle(),
            company.getSocial().getInstagramHandle(),
            company.getSocial().getXingHandle(),
            company.getSocial().getLinkedInHandle()
        )
    );
  }

  // TODO: Use automatic mapping, something like ModelMapper or MapStruct, but ensure safety
  private Company mapToEntity(CompanyDto companyDto) {
    var company = new Company();
    company.setInformation(new CompanyInformation(
        companyDto.getName(),
        companyDto.getFoundingDate(),
        companyDto.getNumberOfEmployees(),
        companyDto.getHomepage(),
        companyDto.getContactEmail(),
        companyDto.getVita()
    ));
    company.setHeadquarter(new Quarter(companyDto.getHeadquarter()));
    company.setQuarters(companyDto.getQuarters().stream().map(Quarter::new).collect(Collectors.toSet()));
    company.setBranches(companyDto.getBranches().stream().map(Branch::new).collect(Collectors.toSet()));
    company.setLanguages(languageService.findAllByIsoIdentifier(companyDto.getLanguages()));
    company.setSocial(new SocialMedia(
        companyDto.getSocial().getFacebookHandle(),
        companyDto.getSocial().getTwitterHandle(),
        companyDto.getSocial().getInstagramHandle(),
        companyDto.getSocial().getXingHandle(),
        companyDto.getSocial().getLinkedInHandle()
    ));
    return company;
  }
}
