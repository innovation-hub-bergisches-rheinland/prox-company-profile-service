package de.innovationhub.prox.companyprofileservice.application.controller;

import de.innovationhub.prox.companyprofileservice.application.service.CompanyService;
import de.innovationhub.prox.companyprofileservice.domain.dtos.company.CompanyDto;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
public class CompanyProfileController {

  private final CompanyService companyService;

  @Autowired
  public CompanyProfileController(
      CompanyService companyService) {
    this.companyService = companyService;
  }

  @PostMapping(value = "/orgs/{id}/profile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CompanyDto> createOrganizationProfile(@PathVariable UUID id,
      @RequestBody @Valid CompanyDto companyDto) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(this.companyService.saveCompany(id, companyDto));
  }

  @GetMapping(value= "/orgs/{id}/profile", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CompanyDto> getOrganizationProfile(@PathVariable UUID id) {
    return companyService.getCompanyOfOrg(id)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @GetMapping(value = "/orgs/{id}/profile/logo", produces = "image/*")
  public ResponseEntity<byte[]> getCompanyLogo(@PathVariable UUID id) {
    var optCompanyLogo = companyService.getOrganizationLogo(id);
    if(optCompanyLogo.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    var companyLogo = optCompanyLogo.get();
    try(InputStream is = this.companyService.getOrganizationLogoAsStream(companyLogo)) {
      return ResponseEntity.status(HttpStatus.OK)
          .header("Content-Type", companyLogo.getMimeType())
          .body(is.readAllBytes());
    } catch (IOException e) {
      log.error("Error reading organization logo of org with id {}", id);
      log.error("Reading Input Stream threw exception: ", e);
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping(value = "/orgs/{id}/profile/logo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> setCompanyLogo(@PathVariable UUID id, @RequestPart(value = "image", required = true)
      MultipartFile image) {
    try {
      return this.companyService.saveOrganizationLogo(id, image.getInputStream())
          .map(logo -> ResponseEntity.status(HttpStatus.OK).build())
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    } catch (IOException e) {
      log.error("Error reading organization logo of org with id {}", id);
      log.error("Reading Input Stream threw exception: ", e);
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping(value = "/orgs/{id}/profile/logo")
  public ResponseEntity<Void> deleteCompanyLogo(@PathVariable UUID id) {
    this.companyService.deleteOrganizationLogo(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
