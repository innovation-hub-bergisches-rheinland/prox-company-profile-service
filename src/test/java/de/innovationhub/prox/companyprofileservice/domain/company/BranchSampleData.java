package de.innovationhub.prox.companyprofileservice.domain.company;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

@Getter
public class BranchSampleData {
  private final Branch SAMPLE_BRANCH_1;
  private final Branch SAMPLE_BRANCH_2;
  private final Branch SAMPLE_BRANCH_3;
  private final Set<Branch> SAMPLE_BRANCHES;

  public BranchSampleData() {
    this.SAMPLE_BRANCH_1 = new Branch("A");
    this.SAMPLE_BRANCH_2 = new Branch("B");
    this.SAMPLE_BRANCH_3 = new Branch("C");
    this.SAMPLE_BRANCHES =
        new HashSet<>(Arrays.asList(SAMPLE_BRANCH_1, SAMPLE_BRANCH_2, SAMPLE_BRANCH_3));
  }
}
