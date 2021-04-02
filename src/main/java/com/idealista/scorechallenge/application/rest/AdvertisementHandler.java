package com.idealista.scorechallenge.application.rest;

import com.idealista.scorechallenge.domain.model.Advertisement;
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

  /**
   * Calculate scores of all existing advertisements
   */
  public Mono<ServerResponse> calculateScores(ServerRequest request) {
    return Mono.empty();
  }
}
