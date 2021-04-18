package de.innovationhub.prox.companyprofileservice.domain.company;

import de.innovationhub.prox.companyprofileservice.domain.language.LanguageSampleData;
import java.util.Set;
import lombok.Getter;

@Getter
public class CompanySampleData {

  private final Company SAMPLE_COMPANY_1;

  private final Company SAMPLE_COMPANY_2;

  private final Set<Company> SAMPLE_COMPANIES;

  public CompanySampleData() {
    var branchSampleData = new BranchSampleData();
    var quarterSampleData = new QuarterSampleData();
    var languageSampleData = new LanguageSampleData();

    this.SAMPLE_COMPANY_1 = new Company(
        new CompanyInformation("Company name", "18-04-2021", "about 200", "www.example.org", "Lorem Ipsum"),
        quarterSampleData.getSAMPLE_QUARTER_1(),
        quarterSampleData.getSAMPLE_QUARTERS(), languageSampleData.getSAMPLE_LANGUAGES(),
        branchSampleData.getSAMPLE_BRANCHES()
    );

    this.SAMPLE_COMPANY_2 = new Company(
        new CompanyInformation("Company name 2", "19-04-2021", "about 200", "www.example.org", "Lorem Ipsum"),
        quarterSampleData.getSAMPLE_QUARTER_2(),
        quarterSampleData.getSAMPLE_QUARTERS(),
        languageSampleData.getSAMPLE_LANGUAGES(),
        branchSampleData.getSAMPLE_BRANCHES()
    );

    this.SAMPLE_COMPANIES = Set.of(SAMPLE_COMPANY_1, SAMPLE_COMPANY_2);
  }
}
