package de.innovationhub.prox.companyprofileservice.domain.company;

import java.util.Set;
import lombok.Getter;

@Getter
public class QuarterSampleData {
  private final Quarter SAMPLE_QUARTER_1;
  private final Quarter SAMPLE_QUARTER_2;
  private final Quarter SAMPLE_QUARTER_3;
  private final Set<Quarter> SAMPLE_QUARTERS;

  public QuarterSampleData() {
    this.SAMPLE_QUARTER_1 = new Quarter("Germany");
    this.SAMPLE_QUARTER_2 = new Quarter("England");
    this.SAMPLE_QUARTER_3 = new Quarter("Russia");
    this.SAMPLE_QUARTERS = Set.of(SAMPLE_QUARTER_1, SAMPLE_QUARTER_2, SAMPLE_QUARTER_3);
  }
}
