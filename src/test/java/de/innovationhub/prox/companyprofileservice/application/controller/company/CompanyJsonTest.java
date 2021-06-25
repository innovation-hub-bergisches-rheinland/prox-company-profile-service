package de.innovationhub.prox.companyprofileservice.application.controller.company;

import static org.assertj.core.api.Assertions.assertThat;

import de.innovationhub.prox.companyprofileservice.domain.company.Branch;
import de.innovationhub.prox.companyprofileservice.domain.company.Company;
import de.innovationhub.prox.companyprofileservice.domain.company.CompanySampleData;
import de.innovationhub.prox.companyprofileservice.domain.company.SocialMedia;
import de.innovationhub.prox.companyprofileservice.domain.company.SocialMediaType;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

@JsonTest
class CompanyJsonTest {

  @Autowired JacksonTester<Company> json;

  @Test
  void testSerialize() throws Exception {
    var company = new CompanySampleData().getSAMPLE_COMPANY_1();

    JsonContent<Company> result = this.json.write(company);

    System.out.println(result.getJson());

    assertThat(result).extractingJsonPathValue("$.id").isEqualTo(company.getId().toString());
    assertThat(result)
        .extractingJsonPathValue("$.information.name")
        .isEqualTo(company.getInformation().getName());
    assertThat(result)
        .extractingJsonPathValue("$.information.foundingDate")
        .isEqualTo(company.getInformation().getFoundingDate());
    assertThat(result)
        .extractingJsonPathValue("$.information.numberOfEmployees")
        .isEqualTo(company.getInformation().getNumberOfEmployees());
    assertThat(result)
        .extractingJsonPathValue("$.information.homepage")
        .isEqualTo(company.getInformation().getHomepage());
    assertThat(result)
        .extractingJsonPathValue("$.information.vita")
        .isEqualTo(company.getInformation().getVita());
    assertThat(result)
        .extractingJsonPathValue("$.information.contactEmail")
        .isEqualTo(company.getInformation().getContactEmail());

    assertThat(result)
        .extractingJsonPathArrayValue("$.branches[*].branchName")
        .containsExactlyInAnyOrderElementsOf(
            company.getBranches().stream().map(Branch::getBranchName).collect(Collectors.toList()));

    assertThat(result)
        .extractingJsonPathArrayValue("$.socialMedia[*].account")
        .containsExactlyInAnyOrderElementsOf(
            company.getSocialMedia().stream()
                .map(SocialMedia::getAccount)
                .collect(Collectors.toList()));

    assertThat(result)
        .extractingJsonPathArrayValue("$.socialMedia[*].type")
        .containsExactlyInAnyOrderElementsOf(
            company.getSocialMedia().stream()
                .map(s -> s.getType().toString())
                .collect(Collectors.toList()));
  }

  @Test
  void testDeserialize() throws Exception {
    String jsonContent =
        "{\"id\":\"d8004470-676a-4511-97c3-7217d37ae4b8\",\"information\":{\"name\":\"Company name\",\"foundingDate\":\"18-04-2021\",\"numberOfEmployees\":\"about 200\",\"homepage\":\"www.example.org\",\"vita\":\"Lorem Ipsum\",\"contactEmail\":\"test@example.org\"},\"branches\":[{\"branchName\":\"Automotive\"},{\"branchName\":\"Industrie 4.0\"}],\"socialMedia\":[{\"type\":\"FACEBOOK\",\"account\":\"test\"}]}";

    Company company = this.json.parse(jsonContent).getObject();

    assertThat(company.getId()).isEqualTo(UUID.fromString("d8004470-676a-4511-97c3-7217d37ae4b8"));

    assertThat(company.getInformation().getName()).isEqualTo("Company name");
    assertThat(company.getInformation().getFoundingDate()).isEqualTo("18-04-2021");
    assertThat(company.getInformation().getHomepage()).isEqualTo("www.example.org");
    assertThat(company.getInformation().getNumberOfEmployees()).isEqualTo("about 200");
    assertThat(company.getInformation().getVita()).isEqualTo("Lorem Ipsum");
    assertThat(company.getInformation().getContactEmail()).isEqualTo("test@example.org");

    assertThat(company.getBranches())
        .containsExactlyInAnyOrderElementsOf(
            Arrays.asList(new Branch("Automotive"), new Branch("Industrie 4.0")));

    assertThat(company.getSocialMedia())
        .containsExactlyInAnyOrderElementsOf(
            Arrays.asList(new SocialMedia(SocialMediaType.FACEBOOK, "test")));
  }
}
