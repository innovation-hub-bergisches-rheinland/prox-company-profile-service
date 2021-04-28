package de.innovationhub.prox.companyprofileservice.application.controller.company;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.innovationhub.prox.companyprofileservice.application.security.SecurityService;
import de.innovationhub.prox.companyprofileservice.application.service.company.CompanyService;
import de.innovationhub.prox.companyprofileservice.domain.company.Company;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanySampleData;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CompanyControllerSecurityTest {

  @Autowired private WebApplicationContext context;

  @MockBean SecurityService securityService;

  @MockBean CompanyService companyService;

  private MockMvc mvc;

  @BeforeEach
  void setup() {
    mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
  }

  @Test
  void givenUnauthorizedRequestOnPostCompanies_shouldFailWithUnauthorized() throws Exception {
    mvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(roles = {"company-manager"})
  void givenAuthorizedRequestOnPostCompaniesUserIsOwnerOfCompany_shouldFailWithForbidden()
      throws Exception {
    when(securityService.authenticatedUserIsNotOwnerOfAnyCompany()).thenReturn(false);
    mvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = {"company-manager"})
  void givenAuthorizedRequestOnPostCompaniesUserIsOwnerOfCompany_shouldSucceedWithCreated()
      throws Exception {
    when(securityService.authenticatedUserIsNotOwnerOfAnyCompany()).thenReturn(true);
    when(companyService.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    mvc.perform(
            post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    new ObjectMapper()
                        .writeValueAsString(new CompanySampleData().getSAMPLE_COMPANY_1())))
        .andExpect(status().isCreated());

    verify(companyService).save(any());
  }

  @Test
  void givenUnauthorizedRequestOnPutCompanies_shouldFailWithUnauthorized() throws Exception {
    mvc.perform(put("/companies/{id}", UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(roles = {"company-manager"})
  void givenAuthorizedRequestOnPutCompaniesUserIsNotOwnerOfCompany_shouldFailWithForbidden()
      throws Exception {
    when(securityService.authenticatedUserIsOwnerOfCompany(any())).thenReturn(false);
    mvc.perform(put("/companies/{id}", UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = {"company-manager"})
  void givenAuthorizedRequestOnPutCompaniesUserIsOwnerOfCompany_shouldSucceedWithOk()
      throws Exception {
    when(securityService.authenticatedUserIsOwnerOfCompany(any())).thenReturn(true);
    when(companyService.update(any(), any())).thenAnswer(invocation -> invocation.getArgument(1));

    Company company = new CompanySampleData().getSAMPLE_COMPANY_1();

    mvc.perform(
            put("/companies/{id}", company.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(company)))
        .andExpect(status().isOk());

    verify(companyService).update(any(), any());
  }

  @Test
  void givenUnauthorizedRequestOnPutCompanyLanguages_shouldFailWithUnauthorized() throws Exception {
    mvc.perform(
            put("/companies/{id}/languages", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(roles = {"company-manager"})
  void givenAuthorizedRequestOnPutCompanyLanguagesUserIsNotOwnerOfCompany_shouldFailWithForbidden()
      throws Exception {
    when(securityService.authenticatedUserIsOwnerOfCompany(any())).thenReturn(false);
    mvc.perform(
            put("/companies/{id}/languages", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = {"company-manager"})
  void givenAuthorizedRequestOnPutCompanyLanguagesUserIsOwnerOfCompany_shouldSucceedWithOk()
      throws Exception {
    when(securityService.authenticatedUserIsOwnerOfCompany(any())).thenReturn(true);
    when(companyService.setCompanyLanguages(any(), any()))
        .thenReturn(new CompanySampleData().getSAMPLE_COMPANY_2());

    mvc.perform(
            put("/companies/{id}/languages", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content("[]"))
        .andExpect(status().isOk());

    verify(companyService).setCompanyLanguages(any(), any());
  }
}
