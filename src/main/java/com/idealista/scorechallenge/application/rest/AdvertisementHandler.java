package com.idealista.scorechallenge.application.rest;

import com.idealista.scorechallenge.application.model.AdvertisementRequestDto;
import com.idealista.scorechallenge.domain.model.Advertisement;
import com.idealista.scorechallenge.domain.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
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
    log.info("[ PUT ] --> /api/private/v1/advertisements/scores");
    return advertisementService.calculateScores().then(ServerResponse.ok().build());
  }

  /**
   * Get all advertisements for user
   */
  public Mono<ServerResponse> getAll(ServerRequest request) {
    log.info("[ GET ] --> /api/public/v1/advertisements");
    return advertisementService.findAllNoIrrelevant()
        .collectList()
        .flatMap(advertisementDtos -> ServerResponse.ok().bodyValue(advertisementDtos));
  }

  /**
   * Get all irrelevant advertisements
   */
  public Mono<ServerResponse> getAllIrrelevant(ServerRequest request) {
    log.info("[ GET ] --> /api/private/v1/advertisements");
    return advertisementService.findAllIrrelevant()
        .collectList()
        .flatMap(advertisementDtos -> ServerResponse.ok().bodyValue(advertisementDtos));
  }

  /**
   * Create an advertisement
   */
  public Mono<ServerResponse> create(ServerRequest request) {
    log.info("[ POST ] --> /api/private/v1/advertisements");
    return fromValidatedBody(request)
        .flatMap(advertisementRequestDto -> advertisementService.create(Mono.just(advertisementRequestDto)))
        .then(ServerResponse.ok().build());
  }

  private Mono<AdvertisementRequestDto> fromValidatedBody(ServerRequest request) {
    return request.bodyToMono(AdvertisementRequestDto.class)
        .flatMap(advertisementRequestDto -> advertisementRequestDto.isNotValid()
            ? Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST)) : Mono.just(advertisementRequestDto))
        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST)));
  }
}
