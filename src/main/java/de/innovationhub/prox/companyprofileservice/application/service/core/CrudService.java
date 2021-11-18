package de.innovationhub.prox.companyprofileservice.application.service.core;


import java.util.Optional;

public interface CrudService<T, ID> {
  Iterable<T> getAll();

  Optional<T> getById(ID id);

  T save(T entity);

  T update(ID id, T entity);

  void deleteById(ID id);
}
