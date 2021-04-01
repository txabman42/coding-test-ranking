package com.idealista.scorechallenge.domain.repository;

import com.idealista.scorechallenge.domain.model.BaseEntity;
import reactor.core.publisher.Mono;

/**
 * Interface for generic CRUD operations on a repository for a specific type.
 * This repository follows reactive paradigms.
 */
public interface BaseReactiveCrudRepository<T extends BaseEntity, ID> {

  /**
   * Saves a given entity
   *
   * @param entity Entity to be persisted
   * @param <S>    Type of the entity
   * @return Observable of the persisted entity
   */
  <S extends T> Mono<S> save(Mono<S> entity);
}
