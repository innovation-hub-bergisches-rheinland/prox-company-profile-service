package de.innovationhub.prox.companyprofileservice.application.exception.core;

public abstract class CustomEntityNotFoundException extends CustomRuntimeException {

  protected CustomEntityNotFoundException(String message) {
    super(message);
  }
}