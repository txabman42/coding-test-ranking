package com.idealista.scorechallenge.infrastructure.inmemory.repository;

import com.idealista.scorechallenge.domain.model.BaseEntity;
import com.idealista.scorechallenge.domain.repository.BaseReactiveCrudRepository;
import com.idealista.scorechallenge.infrastructure.inmemory.database.BaseDatabase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@RequiredArgsConstructor
public abstract class BaseReactiveInMemoryRepository<T extends BaseEntity, ID> implements BaseReactiveCrudRepository<T, ID> {

  private final BaseDatabase<T, ID> baseDatabase;

  @Override
  public <S extends T> Mono<S> save(Mono<S> monoEntity) {
    Mono<S> s = monoEntity.doOnNext(entity -> baseDatabase.getDb().put((ID) entity.getUuid(), entity));
    return s;
  }

  @Override
  public <S extends T> Flux<S> findAll() {
    return Flux.fromIterable((Collection<S>) baseDatabase.getDb().values());
  }
}
