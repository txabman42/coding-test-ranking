package com.idealista.scorechallenge.domain.service;

import com.idealista.scorechallenge.application.model.PublicAdvertisementDto;
import com.idealista.scorechallenge.application.model.AdvertisementRequestDto;
import com.idealista.scorechallenge.application.model.QualityAdvertisementDto;
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
  Flux<PublicAdvertisementDto> findAllNoIrrelevant();

  /**
   * Finds all irrelevant {@link Advertisement}
   *
   * @return Found irrelevant advertisements ordered by irrelevantSince
   */
  Flux<QualityAdvertisementDto> findAllIrrelevant();
}
