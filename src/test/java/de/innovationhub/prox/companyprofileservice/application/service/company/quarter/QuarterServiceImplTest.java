package de.innovationhub.prox.companyprofileservice.application.service.company.quarter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.innovationhub.prox.companyprofileservice.domain.company.quarter.Quarter;
import de.innovationhub.prox.companyprofileservice.domain.company.quarter.QuarterRepository;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

@Import({QuarterServiceImpl.class})
@SpringBootTest
class QuarterServiceImplTest {

  @MockBean QuarterRepository quarterRepository;

  @Autowired QuarterService quarterService;

  private Quarter sampleQuarter = new Quarter("test1");
  private Quarter sampleQuarter2 = new Quarter("test2");
  private Set<Quarter> sampleQuarters = Set.of(sampleQuarter, sampleQuarter2);

  @DisplayName("Should return all quarters")
  @Test
  void shouldReturnAllQuarters() {
    when(quarterRepository.findAll()).thenReturn(sampleQuarters);

    assertThat(quarterService.getAll()).containsExactlyInAnyOrderElementsOf(sampleQuarters);

    verify(quarterRepository).findAll();
  }

  @DisplayName("Should return quarter")
  @Test
  void shouldReturnQuarter() {
    when(quarterRepository.findById(eq(sampleQuarter.getId())))
        .thenReturn(Optional.of(sampleQuarter));

    assertThat(quarterService.getById(sampleQuarter.getId())).get().isEqualTo(sampleQuarter);

    verify(quarterRepository).findById(eq(sampleQuarter.getId()));
  }

  @DisplayName("Should return empty optional")
  @Test
  void shouldReturnEmptyOptional() {
    when(quarterRepository.findById(any())).thenReturn(Optional.empty());

    assertThat(quarterService.getById(sampleQuarter.getId())).isEmpty();

    verify(quarterRepository).findById(eq(sampleQuarter.getId()));
  }

  @DisplayName("Should save quarter")
  @Test
  void shouldSaveQuarter() {
    when(quarterRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    assertThat(quarterService.save(sampleQuarter)).isEqualTo(sampleQuarter);

    verify(quarterRepository).save(eq(sampleQuarter));
  }

  @DisplayName("Should update quarter")
  @Test
  void shouldUpdateQuarter() {
    when(quarterRepository.findById(eq(sampleQuarter.getId())))
        .thenReturn(Optional.of(sampleQuarter));
    when(quarterRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    var updated = quarterService.update(sampleQuarter.getId(), sampleQuarter2);

    // Verify that id is not updated
    assertThat(updated.getId()).isEqualTo(sampleQuarter.getId());

    // TODO: Test that the other properties were set

    verify(quarterRepository).findById(eq(sampleQuarter.getId()));
    verify(quarterRepository).save(any());
  }

  @DisplayName("Should delete quarter")
  @Test
  void shouldDeleteQuarter() {
    quarterService.deleteById(sampleQuarter.getId());

    verify(quarterRepository).deleteById(eq(sampleQuarter.getId()));
  }
}
