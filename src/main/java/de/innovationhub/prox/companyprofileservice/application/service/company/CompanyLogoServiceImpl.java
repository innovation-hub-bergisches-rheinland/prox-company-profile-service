package de.innovationhub.prox.companyprofileservice.application.service.company;

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
import org.springframework.data.util.Pair;
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
  public Optional<InputStream> getCompanyLogoAsStream(CompanyLogo companyLogo) {
    if(companyLogo != null) {
      InputStream is = companyLogoStore.getContent(companyLogo);
      return Optional.of(is);
    }
    return Optional.empty();
  }

  @Override
  public Optional<CompanyLogo> setCompanyLogo(CompanyLogo companyLogo, InputStream inputStream)
      throws IOException {
    if(companyLogo == null) {
      companyLogo = new CompanyLogo();
    }

    Detector detector = tikaConfig.getDetector();
    byte[] bytes = inputStream.readAllBytes();
    TikaInputStream tikaInputStream = TikaInputStream.get(new ByteArrayInputStream(bytes));
    MediaType mediaType = detector.detect(tikaInputStream, new Metadata());
    if(!mediaType.getType().equals("image")) {
      throw new RuntimeException("Logo must be an image");
    }
    companyLogo.setMimeType(mediaType.getType() + "/" + mediaType.getSubtype());
    CompanyLogo companyLogo1 = companyLogoStore.setContent(companyLogo, new ByteArrayInputStream(bytes));
    companyLogo = this.save(companyLogo1);

    return Optional.of(companyLogo);
  }

  @Override
  public Optional<CompanyLogo> deleteCompanyLogo(CompanyLogo companyLogo) {
    if(companyLogo != null) {
      var cl = companyLogoStore.unsetContent(companyLogo);
      this.deleteById(cl.getId());
      return Optional.of(cl);
    }
    return Optional.empty();
  }


}
