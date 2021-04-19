package de.innovationhub.prox.companyprofileservice.application.controller.company;

import de.innovationhub.prox.companyprofileservice.application.service.company.CompanyLogoService;
import de.innovationhub.prox.companyprofileservice.application.service.company.CompanyService;
import de.innovationhub.prox.companyprofileservice.domain.company.Company;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyLogo;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CompanyLogoControllerImpl implements CompanyLogoController {

  private final CompanyLogoService companyLogoService;
  private final CompanyService companyService;

  public CompanyLogoControllerImpl(CompanyLogoService companyLogoService, CompanyService companyService) {
    this.companyLogoService = companyLogoService;
    this.companyService = companyService;
  }

  @Override
  public ResponseEntity<byte[]> getCompanyLogo(UUID id) {
    Company company = this.companyService.getById(id).orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND, "Image of company with id " + id + " not found"));
    var companyLogo = company.getCompanyLogo();
    if(companyLogo == null) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Image of company with id " + id + " not found");
    }
    try(InputStream is = companyLogoService.getCompanyLogo(companyLogo)) {
      var bytes = is.readAllBytes();
      return ResponseEntity.status(HttpStatus.OK).header("Content-Type", companyLogo.getMimeType()).body(bytes);
    } catch (IOException ioException) {
      ioException.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Override
  public ResponseEntity<Void> postCompanyLogo(UUID id, MultipartFile image,
      HttpServletRequest request) {
    Company company = this.companyService.getById(id).orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND, "Image of company with id " + id + " not found"));
    try {
      CompanyLogo companyLogo = company.getCompanyLogo();
      if(companyLogo == null) {
        companyLogo = new CompanyLogo();
      }
      companyLogo = this.companyLogoService.setCompanyLogo(companyLogo, image.getInputStream());
      company.setCompanyLogo(companyLogo);
      this.companyService.save(company);
      return ResponseEntity.ok().build();
    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Override
  public ResponseEntity<Void> deleteCompanyLogo(UUID id, HttpServletRequest request) {
    Company company = this.companyService.getById(id).orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND, "Image of company with id " + id + " not found"));
    var companyLogo = company.getCompanyLogo();
    if(companyLogo == null) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Image of company with id " + id + " not found");
    }
    company.setCompanyLogo(null);
    this.companyService.save(company);
    this.companyLogoService.deleteCompanyLogo(companyLogo);
    this.companyLogoService.deleteById(companyLogo.getId());
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
