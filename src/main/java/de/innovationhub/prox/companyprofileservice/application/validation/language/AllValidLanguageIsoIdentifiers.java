package de.innovationhub.prox.companyprofileservice.application.validation.language;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = AllValidLanguageIsoIdentifiersValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AllValidLanguageIsoIdentifiers {
  String message() default "Invalid List of ISO-639-1 Identifiers";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
