package de.innovationhub.prox.companyprofileservice.application.service.company.quarter;

import de.innovationhub.prox.companyprofileservice.application.exception.core.CustomEntityNotFoundException;
import de.innovationhub.prox.companyprofileservice.domain.company.quarter.Quarter;
import de.innovationhub.prox.companyprofileservice.domain.company.quarter.QuarterRepository;
import java.util.Optional;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuarterServiceImpl implements QuarterService {

  private final QuarterRepository quarterRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public QuarterServiceImpl(QuarterRepository quarterRepository) {
    this.quarterRepository = quarterRepository;
    this.modelMapper = new ModelMapper();
  }

  @Override
  public Iterable<Quarter> getAll() {
    return quarterRepository.findAll();
  }

  @Override
  public Optional<Quarter> getById(UUID uuid) {
    return quarterRepository.findById(uuid);
  }

  @Override
  public Quarter save(Quarter entity) {
    return quarterRepository.save(entity);
  }

  @Override
  public Quarter update(UUID id, Quarter entity) {
    return this.getById(id)
        .map(
            q -> {
              modelMapper.map(entity, q);
              return q;
            })
        .map(quarterRepository::save)
        .orElseThrow(
            () -> new CustomEntityNotFoundException("Quarter with id " + id + " not found"));
  }

  @Override
  public void deleteById(UUID uuid) {
    quarterRepository.deleteById(uuid);
  }
}
