package de.innovationhub.prox.companyprofileservice.application.exception.company;

import de.innovationhub.prox.companyprofileservice.application.exception.core.CustomEntityNotFoundException;

public class CompanyNotFoundException extends CustomEntityNotFoundException {
  public CompanyNotFoundException() {
    super("Company not found");
  }
}
