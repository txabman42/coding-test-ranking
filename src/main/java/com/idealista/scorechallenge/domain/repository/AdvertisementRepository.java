package com.idealista.scorechallenge.domain.repository;

import com.idealista.scorechallenge.domain.model.Advertisement;

import java.util.UUID;

/**
 * Interface that defines the Repository to store ${@link Advertisement}
 */
public interface AdvertisementRepository extends BaseReactiveCrudRepository<Advertisement, UUID> {
}
