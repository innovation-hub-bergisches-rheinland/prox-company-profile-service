package de.innovationhub.prox.companyprofileservice.application.controller.company.quarter;

import static org.assertj.core.api.Assertions.assertThat;

import de.innovationhub.prox.companyprofileservice.domain.company.quarter.Quarter;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

@JsonTest
class QuarterJsonTest {

  @Autowired JacksonTester<Quarter> json;

  @Test
  void testSerialize() throws Exception {
    var quarter = new Quarter("test");

    JsonContent<Quarter> result = this.json.write(quarter);

    System.out.println(result.getJson());

    assertThat(result).extractingJsonPathValue("$.id").isEqualTo(quarter.getId().toString());
    assertThat(result).extractingJsonPathValue("$.location").isEqualTo(quarter.getLocation());
  }

  @Test
  void testDeserialize() throws Exception {
    String jsonContent = "{\"id\":\"d8004470-676a-4511-97c3-7217d37ae4b8\",\"location\":\"test\"}";

    Quarter quarter = this.json.parse(jsonContent).getObject();

    assertThat(quarter.getId()).isEqualTo(UUID.fromString("d8004470-676a-4511-97c3-7217d37ae4b8"));

    assertThat(quarter.getLocation()).isEqualTo("test");
  }
}
