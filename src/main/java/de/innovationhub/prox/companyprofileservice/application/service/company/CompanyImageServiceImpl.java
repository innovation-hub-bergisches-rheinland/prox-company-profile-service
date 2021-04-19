package de.innovationhub.prox.companyprofileservice.application.service.company;

import de.innovationhub.prox.companyprofileservice.application.exception.company.CompanyNotFoundException;
import de.innovationhub.prox.companyprofileservice.domain.company.Company;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyImage;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyImageRepository;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyImageStore;
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
public class CompanyImageServiceImpl implements CompanyImageService {

  private final CompanyImageRepository companyImageRepository;
  private final CompanyImageStore companyImageStore;
  private final ModelMapper modelMapper;
  private final TikaConfig tikaConfig;

  @Autowired
  public CompanyImageServiceImpl(CompanyImageRepository companyImageRepository, CompanyImageStore companyImageStore) {
    this.companyImageRepository = companyImageRepository;
    this.companyImageStore = companyImageStore;
    this.modelMapper = new ModelMapper();
    this.tikaConfig = TikaConfig.getDefaultConfig();
  }

  @Override
  public Iterable<CompanyImage> getAll() {
    return this.companyImageRepository.findAll();
  }

  @Override
  public Optional<CompanyImage> getById(UUID uuid) {
    return this.companyImageRepository.findById(uuid);
  }

  @Override
  public CompanyImage save(CompanyImage entity) {
    return this.companyImageRepository.save(entity);
  }

  @Override
  public CompanyImage update(UUID uuid, CompanyImage entity) {
    return this.getById(uuid)
        .map(
            c -> {
              modelMapper.map(entity, c);
              return c;
            })
        .map(companyImageRepository::save)
        .orElseThrow(RuntimeException::new);
  }

  @Override
  public void deleteById(UUID uuid) {
    this.companyImageRepository.deleteById(uuid);
  }


  @Override
  public InputStream getCompanyImage(CompanyImage companyImage) {
    InputStream is = companyImageStore.getContent(companyImage);
    return is;
  }

  @Override
  public CompanyImage setCompanyImage(CompanyImage companyImage, InputStream inputStream)
      throws IOException {
    Detector detector = tikaConfig.getDetector();
    byte[] bytes = inputStream.readAllBytes();
    TikaInputStream tikaInputStream = TikaInputStream.get(new ByteArrayInputStream(bytes));
    MediaType mediaType = detector.detect(tikaInputStream, new Metadata());
    companyImage.setMimeType(mediaType.getType() + "/" + mediaType.getSubtype());
    CompanyImage companyImage1 = companyImageStore.setContent(companyImage, new ByteArrayInputStream(bytes));
    return this.save(companyImage1);
  }

  @Override
  public CompanyImage deleteCompanyImage(CompanyImage companyImage) {
    return companyImageStore.unsetContent(companyImage);
  }
}
