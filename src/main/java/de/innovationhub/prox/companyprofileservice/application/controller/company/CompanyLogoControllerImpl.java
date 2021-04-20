package de.innovationhub.prox.companyprofileservice.application.controller.company;

import de.innovationhub.prox.companyprofileservice.application.service.company.CompanyLogoService;
import de.innovationhub.prox.companyprofileservice.application.service.company.CompanyService;
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

  public CompanyLogoControllerImpl(
      CompanyLogoService companyLogoService) {
    this.companyLogoService = companyLogoService;
  }

  @Override
  public ResponseEntity<byte[]> getCompanyLogo(UUID id) {
    var optCompanyLogo = companyLogoService.getCompanyLogo(id);
    var optIs = optCompanyLogo.flatMap(companyLogoService::getCompanyLogoAsStream);
    if(optIs.isEmpty()) {
      //CompanyLogo Entity not found
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

      try (InputStream is = optIs.get()) {
        var bytes = is.readAllBytes();
        return ResponseEntity.status(HttpStatus.OK)
            .header("Content-Type", optCompanyLogo.get().getMimeType()).body(bytes);
      } catch (IOException ioException) {
        ioException.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
  }

  @Override
  public ResponseEntity<Void> postCompanyLogo(UUID id, MultipartFile image,
      HttpServletRequest request) {
    try {
      this.companyLogoService.setCompanyLogo(id, image.getInputStream()).orElseThrow(() -> new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Image could not be saved"));
      return ResponseEntity.ok().build();
    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Override
  public ResponseEntity<Void> deleteCompanyLogo(UUID id, HttpServletRequest request) {
    var optLogo = this.companyLogoService.deleteCompanyLogo(id);
    if(optLogo.isPresent()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }
}
