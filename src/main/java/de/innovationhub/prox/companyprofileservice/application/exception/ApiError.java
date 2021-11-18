package de.innovationhub.prox.companyprofileservice.application.exception;


import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError {
  @NotNull private int status;
  @NotNull private String error;
  @NotNull private String message;
}
