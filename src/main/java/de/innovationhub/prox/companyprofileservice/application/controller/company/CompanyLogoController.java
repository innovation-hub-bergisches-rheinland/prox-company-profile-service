package de.innovationhub.prox.companyprofileservice.application.controller.company;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("companies")
public interface CompanyLogoController {
  @GetMapping(value = "/{id}/logo", produces = "image/*")
  ResponseEntity<byte[]> getCompanyLogo(@PathVariable UUID id);

  @PostMapping(
      value = "/{id}/logo",
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  ResponseEntity<Void> postCompanyLogo(@PathVariable UUID id,
      @RequestPart(value = "image", required = true)
          MultipartFile image,
      HttpServletRequest request);


  @DeleteMapping(value = "/{id}/logo")
  ResponseEntity<Void> deleteCompanyLogo(@PathVariable UUID id, HttpServletRequest request);
}