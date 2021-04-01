package com.idealista.scorechallenge.infrastructure.inmemory.repository;

import com.idealista.scorechallenge.domain.model.Advertisement;
import com.idealista.scorechallenge.domain.repository.AdvertisementRepository;
import com.idealista.scorechallenge.infrastructure.inmemory.database.BaseDatabase;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class AdvertisementInMemoryRepository extends BaseReactiveInMemoryRepository<Advertisement, UUID> implements AdvertisementRepository {

  public AdvertisementInMemoryRepository(BaseDatabase<Advertisement, UUID> baseDatabase) {
    super(baseDatabase);
  }
}
