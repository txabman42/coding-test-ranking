package com.idealista.scorechallenge.application.rest;

import com.idealista.scorechallenge.domain.model.Advertisement;
import com.idealista.scorechallenge.domain.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Handles all request/response for {@link Advertisement} CRUD operations
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AdvertisementHandler {

  private final AdvertisementService advertisementService;

  /**
   * Calculate scores of all existing advertisements
   */
  public Mono<ServerResponse> calculateScores(ServerRequest request) {
    log.info("[ PUT ] --> /advertisements/scores");
    return advertisementService.calculateScores().then(ServerResponse.ok().build());
  }

  /**
   * Get all advertisements for user
   */
  public Mono<ServerResponse> getAll(ServerRequest request) {
    log.info("[ GET ] --> /advertisements");
    return advertisementService.findAllNoIrrelevant()
        .collectList()
        .flatMap(advertisementDtos -> ServerResponse.ok().bodyValue(advertisementDtos));
  }
}
