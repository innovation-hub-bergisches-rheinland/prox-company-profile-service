package de.innovationhub.prox.companyprofileservice.application.controller.company.quarter;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.Mockito.*;

import de.innovationhub.prox.companyprofileservice.application.config.WebConfig;
import de.innovationhub.prox.companyprofileservice.application.hateoas.QuarterRepresentationModelAssembler;
import de.innovationhub.prox.companyprofileservice.application.service.company.quarter.QuarterService;
import de.innovationhub.prox.companyprofileservice.domain.company.quarter.Quarter;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(controllers = QuarterController.class)
@Import({QuarterRepresentationModelAssembler.class, WebConfig.class})
@RunWith(SpringRunner.class)
class QuarterControllerImplTest {
  @MockBean QuarterService quarterService;

  @Autowired private WebApplicationContext context;

  @DisplayName("GET /quarters should return OK")
  @Test
  void getAllQuartersShouldReturnOk() {
    when(quarterService.getAll()).thenReturn(Collections.emptyList());

    given()
        .webAppContextSetup(context)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
        .when()
        .get("/quarters")
        .then()
        .status(HttpStatus.OK)
        .header("Content-Type", MediaTypes.HAL_JSON_VALUE);

    verify(quarterService).getAll();
  }

  @DisplayName("GET /quarters/{id} should return OK")
  @Test
  void getQuarterShouldReturnOk() {
    when(quarterService.getById(any())).thenReturn(Optional.of(new Quarter("test")));

    UUID id = UUID.randomUUID();

    given()
        .webAppContextSetup(context)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
        .when()
        .get("/quarters/{id}", id)
        .then()
        .header("Content-Type", MediaTypes.HAL_JSON_VALUE)
        .status(HttpStatus.OK);

    verify(quarterService).getById(eq(id));
  }

  @DisplayName("GET /quarters/{id} should return NOT_FOUND")
  @Test
  void getQuarterShouldReturnNotFound() {
    when(quarterService.getById(any())).thenReturn(Optional.empty());

    UUID id = UUID.randomUUID();

    given()
        .webAppContextSetup(context)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
        .when()
        .get("/quarters/{id}", id)
        .then()
        .status(HttpStatus.NOT_FOUND);

    verify(quarterService).getById(eq(id));
  }

  @DisplayName("POST /quarters should return CREATED")
  @Test
  void postQuarterShouldReturnCreated() {
    when(quarterService.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    Quarter quarter = new Quarter("location");

    given()
        .webAppContextSetup(context)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .body(quarter)
        .when()
        .post("/quarters")
        .then()
        .status(HttpStatus.CREATED);

    verify(quarterService).save(eq(quarter));
  }

  @DisplayName("POST /quarters should return BAD_REQUEST")
  @Test
  void postQuarterInvalidShouldReturnBadRequest() {
    when(quarterService.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    String quarter = "{\"location\":\"  \"}";

    given()
        .webAppContextSetup(context)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .body(quarter)
        .when()
        .post("/quarters")
        .then()
        .log()
        .all()
        .status(HttpStatus.BAD_REQUEST);

    verify(quarterService, times(0)).save(any());
  }

  @DisplayName("PUT /quarters/{id} should return OK")
  @Test
  void putQuarterShouldReturnOk() {
    when(quarterService.update(any(), any())).thenAnswer(invocation -> invocation.getArgument(1));

    Quarter quarter = new Quarter("test");

    given()
        .webAppContextSetup(context)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .body(quarter)
        .when()
        .put("/quarters/{id}", quarter.getId())
        .then()
        .log()
        .all()
        .status(HttpStatus.OK);

    verify(quarterService).update(eq(quarter.getId()), eq(quarter));
  }

  @DisplayName("PUT /quarters/{id} should return BAD_REQUEST")
  @Test
  void putQuarterInvalidShouldReturnBadRequest() {
    when(quarterService.update(any(), any())).thenAnswer(invocation -> invocation.getArgument(1));

    String quarter = "{\"location\":\"  \"}";

    given()
        .webAppContextSetup(context)
        .header("Accept", MediaTypes.HAL_JSON_VALUE)
        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .body(quarter)
        .when()
        .put("/quarters/{id}", UUID.randomUUID())
        .then()
        .log()
        .all()
        .status(HttpStatus.BAD_REQUEST);

    verify(quarterService, times(0)).update(any(), any());
  }

  @DisplayName("PUT /quarters/{id} should return NOT_FOUND")
  @Test
  void putQuarterInvalidIdShouldReturnNotFound() {
    // TODO
  }
}
