package de.innovationhub.prox.companyprofileservice.application.service.company;


import de.innovationhub.prox.companyprofileservice.domain.company.Company;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyLogo;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyLogoRepository;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyLogoStore;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyLogoServiceImpl implements CompanyLogoService {

  private final CompanyLogoRepository companyLogoRepository;
  private final CompanyLogoStore companyLogoStore;
  private final CompanyService companyService;
  private final ModelMapper modelMapper;
  private final TikaConfig tikaConfig;

  @Autowired
  public CompanyLogoServiceImpl(
      CompanyLogoRepository companyLogoRepository,
      CompanyLogoStore companyLogoStore,
      CompanyService companyService) {
    this.companyLogoRepository = companyLogoRepository;
    this.companyLogoStore = companyLogoStore;
    this.companyService = companyService;
    this.modelMapper = new ModelMapper();
    this.tikaConfig = TikaConfig.getDefaultConfig();
  }

  private Optional<CompanyLogo> getById(UUID uuid) {
    return this.companyLogoRepository.findById(uuid);
  }

  private CompanyLogo save(CompanyLogo entity) {
    return this.companyLogoRepository.save(entity);
  }

  private void deleteById(UUID uuid) {
    this.companyLogoRepository.deleteById(uuid);
  }

  @Override
  public Optional<CompanyLogo> getCompanyLogo(UUID companyId) {
    return this.companyService.getById(companyId).map(Company::getCompanyLogo);
  }

  @Override
  public Optional<InputStream> getCompanyLogoAsStream(CompanyLogo companyLogo) {
    return Optional.of(this.companyLogoStore.getContent(companyLogo));
  }

  @Override
  public Optional<CompanyLogo> setCompanyLogo(UUID companyId, InputStream inputStream)
      throws IOException {
    var optCompany = this.companyService.getById(companyId);
    if (optCompany.isEmpty()) {
      return Optional.empty();
    }
    var company = optCompany.get();
    var companyLogo = company.getCompanyLogo();
    if (companyLogo == null) {
      companyLogo = new CompanyLogo();
    }

    Detector detector = tikaConfig.getDetector();
    byte[] bytes = inputStream.readAllBytes();
    TikaInputStream tikaInputStream = TikaInputStream.get(new ByteArrayInputStream(bytes));
    MediaType mediaType = detector.detect(tikaInputStream, new Metadata());
    if (!mediaType.getType().equals("image")) {
      throw new RuntimeException("Logo must be an image");
    }
    companyLogo.setMimeType(mediaType.getType() + "/" + mediaType.getSubtype());
    CompanyLogo companyLogo1 =
        companyLogoStore.setContent(companyLogo, new ByteArrayInputStream(bytes));
    companyLogo = this.save(companyLogo1);

    company.setCompanyLogo(companyLogo);
    this.companyService.save(company);

    return Optional.of(companyLogo);
  }

  @Override
  public Optional<CompanyLogo> deleteCompanyLogo(UUID companyId) {
    var optCompany = this.companyService.getById(companyId);
    if (optCompany.isEmpty()) {
      return Optional.empty();
    }
    var company = optCompany.get();
    var companyLogo = company.getCompanyLogo();
    if (companyLogo != null) {
      var cl = companyLogoStore.unsetContent(companyLogo);
      company.setCompanyLogo(null);
      this.companyService.save(company);
      this.deleteById(cl.getId());
      return Optional.of(cl);
    }
    return Optional.empty();
  }
}
