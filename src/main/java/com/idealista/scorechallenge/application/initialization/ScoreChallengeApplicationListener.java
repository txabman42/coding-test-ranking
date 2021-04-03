package com.idealista.scorechallenge.application.initialization;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idealista.scorechallenge.application.model.AdvertisementRequestDto;
import com.idealista.scorechallenge.domain.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Order(0)
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "idealista.init.enabled", havingValue = "true", matchIfMissing = true)
public class ScoreChallengeApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

  private final ObjectMapper mapper;
  private final InitialAdvertisements initialAdvertisements;
  private final AdvertisementService advertisementService;

  @Override
  public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
    List<AdvertisementRequestDto> advertisementRequestDtos =
        mapper.convertValue(initialAdvertisements.getAdvertisements(), new TypeReference<>() {
        });
    Flux.fromIterable(advertisementRequestDtos)
        .flatMap(advertisementRequestDto -> advertisementService.create(Mono.just(advertisementRequestDto)))
        .subscribe();
    log.info("All initial advertisements created correctly");
  }
}