package de.innovationhub.prox.companyprofileservice.application.controller.company;

import de.innovationhub.prox.companyprofileservice.application.service.company.CompanyImageService;
import de.innovationhub.prox.companyprofileservice.application.service.company.CompanyService;
import de.innovationhub.prox.companyprofileservice.domain.company.Company;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanyImage;
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
public class CompanyImageControllerImpl implements CompanyImageController {

  private final CompanyImageService companyImageService;
  private final CompanyService companyService;

  public CompanyImageControllerImpl(CompanyImageService companyImageService, CompanyService companyService) {
    this.companyImageService = companyImageService;
    this.companyService = companyService;
  }

  @Override
  public ResponseEntity<byte[]> getCompanyImage(UUID id) {
    Company company = this.companyService.getById(id).orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND, "Image of company with id " + id + " not found"));
    var companyImage = company.getCompanyImage();
    if(companyImage == null) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Image of company with id " + id + " not found");
    }
    try(InputStream is = companyImageService.getCompanyImage(companyImage)) {
      var bytes = is.readAllBytes();
      return ResponseEntity.status(HttpStatus.OK).header("Content-Type", companyImage.getMimeType()).body(bytes);
    } catch (IOException ioException) {
      ioException.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Override
  public ResponseEntity<Void> postCompanyImage(UUID id, MultipartFile image,
      HttpServletRequest request) {
    Company company = this.companyService.getById(id).orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND, "Image of company with id " + id + " not found"));
    try {
      CompanyImage companyImage = company.getCompanyImage();
      if(companyImage == null) {
        companyImage = new CompanyImage();
      }
      companyImage = this.companyImageService.setCompanyImage(companyImage, image.getInputStream());
      company.setCompanyImage(companyImage);
      this.companyService.save(company);
      return ResponseEntity.ok().build();
    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Override
  public ResponseEntity<Void> deleteCompanyImage(UUID id, HttpServletRequest request) {
    Company company = this.companyService.getById(id).orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND, "Image of company with id " + id + " not found"));
    var companyImage = company.getCompanyImage();
    if(companyImage == null) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Image of company with id " + id + " not found");
    }
    company.setCompanyImage(null);
    this.companyService.save(company);
    this.companyImageService.deleteCompanyImage(companyImage);
    this.companyImageService.deleteById(companyImage.getId());
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
