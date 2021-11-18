package de.innovationhub.prox.companyprofileservice.domain.company;


import de.innovationhub.prox.companyprofileservice.domain.company.language.LanguageSampleData;
import de.innovationhub.prox.companyprofileservice.domain.company.quarter.QuarterSampleData;
import java.util.Arrays;
import java.util.HashSet;
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

    this.SAMPLE_COMPANY_1 =
        new Company(
            new CompanyInformation(
                "Company name",
                "18-04-2021",
                "about 200",
                "www.example.org",
                "Lorem Ipsum",
                "test@example.org"),
            quarterSampleData.getSAMPLE_QUARTER_1(),
            quarterSampleData.getSAMPLE_QUARTERS(),
            languageSampleData.getSAMPLE_LANGUAGES(),
            branchSampleData.getSAMPLE_BRANCHES(),
            Set.of(new SocialMedia(SocialMediaType.FACEBOOK, "test")));

    this.SAMPLE_COMPANY_2 =
        new Company(
            new CompanyInformation(
                "Company name 2",
                "19-04-2021",
                "about 200",
                "www.example.org",
                "Lorem Ipsum",
                "test@example.org"),
            quarterSampleData.getSAMPLE_QUARTER_2(),
            quarterSampleData.getSAMPLE_QUARTERS(),
            languageSampleData.getSAMPLE_LANGUAGES(),
            branchSampleData.getSAMPLE_BRANCHES(),
            Set.of(new SocialMedia(SocialMediaType.FACEBOOK, "test")));

    this.SAMPLE_COMPANIES = new HashSet<>(Arrays.asList(SAMPLE_COMPANY_1, SAMPLE_COMPANY_2));
  }
}
