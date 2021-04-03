package com.idealista.scorechallenge.domain.service;

import com.idealista.scorechallenge.application.model.AdvertisementDto;
import com.idealista.scorechallenge.application.model.AdvertisementRequestDto;
import com.idealista.scorechallenge.domain.model.Advertisement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Defines the API for the service which handle the {@link Advertisement}
 */
public interface AdvertisementService {

  /**
   * Create an {@link Advertisement}
   *
   * @param advertisementRequestDtoMono Publisher of the advertisement to be created
   */
  Mono<Void> create(Mono<AdvertisementRequestDto> advertisementRequestDtoMono);

  /**
   * Calculate scores of all {@link Advertisement} without any previous calculation
   */
  Mono<Void> calculateScores();

  /**
   * Finds all no irrelevant {@link Advertisement}
   *
   * @return Found advertisements ordered by score
   */
  Flux<AdvertisementDto> findAllNoIrrelevant();
}
