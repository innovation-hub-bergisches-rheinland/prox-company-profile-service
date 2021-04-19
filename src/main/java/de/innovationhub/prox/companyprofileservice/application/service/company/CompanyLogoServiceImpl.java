package de.innovationhub.prox.companyprofileservice.application.service.company;

import de.innovationhub.prox.companyprofileservice.application.exception.company.CompanyNotFoundException;
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
import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

@Service
public class CompanyLogoServiceImpl implements CompanyLogoService {

  private final CompanyLogoRepository companyLogoRepository;
  private final CompanyLogoStore companyLogoStore;
  private final ModelMapper modelMapper;
  private final TikaConfig tikaConfig;

  @Autowired
  public CompanyLogoServiceImpl(CompanyLogoRepository companyLogoRepository, CompanyLogoStore companyLogoStore) {
    this.companyLogoRepository = companyLogoRepository;
    this.companyLogoStore = companyLogoStore;
    this.modelMapper = new ModelMapper();
    this.tikaConfig = TikaConfig.getDefaultConfig();
  }

  @Override
  public Iterable<CompanyLogo> getAll() {
    return this.companyLogoRepository.findAll();
  }

  @Override
  public Optional<CompanyLogo> getById(UUID uuid) {
    return this.companyLogoRepository.findById(uuid);
  }

  @Override
  public CompanyLogo save(CompanyLogo entity) {
    return this.companyLogoRepository.save(entity);
  }

  @Override
  public CompanyLogo update(UUID uuid, CompanyLogo entity) {
    return this.getById(uuid)
        .map(
            c -> {
              modelMapper.map(entity, c);
              return c;
            })
        .map(companyLogoRepository::save)
        .orElseThrow(RuntimeException::new);
  }

  @Override
  public void deleteById(UUID uuid) {
    this.companyLogoRepository.deleteById(uuid);
  }


  @Override
  public InputStream getCompanyLogo(CompanyLogo companyLogo) {
    InputStream is = companyLogoStore.getContent(companyLogo);
    return is;
  }

  @Override
  public CompanyLogo setCompanyLogo(CompanyLogo companyLogo, InputStream inputStream)
      throws IOException {
    Detector detector = tikaConfig.getDetector();
    byte[] bytes = inputStream.readAllBytes();
    TikaInputStream tikaInputStream = TikaInputStream.get(new ByteArrayInputStream(bytes));
    MediaType mediaType = detector.detect(tikaInputStream, new Metadata());
    companyLogo.setMimeType(mediaType.getType() + "/" + mediaType.getSubtype());
    CompanyLogo companyLogo1 = companyLogoStore.setContent(companyLogo, new ByteArrayInputStream(bytes));
    return this.save(companyLogo1);
  }

  @Override
  public CompanyLogo deleteCompanyLogo(CompanyLogo companyLogo) {
    return companyLogoStore.unsetContent(companyLogo);
  }
}
