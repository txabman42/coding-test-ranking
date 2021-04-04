package com.idealista.scorechallenge.domain.service;

import com.idealista.scorechallenge.application.model.PublicAdvertisementDto;
import com.idealista.scorechallenge.application.model.AdvertisementRequestDto;
import com.idealista.scorechallenge.application.model.QualityAdvertisementDto;
import com.idealista.scorechallenge.domain.configuration.AdvertisementConfigurationProperties;
import com.idealista.scorechallenge.domain.model.Advertisement;
import com.idealista.scorechallenge.domain.model.Building;
import com.idealista.scorechallenge.domain.model.Picture;
import com.idealista.scorechallenge.domain.repository.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdvertisementServiceImpl implements AdvertisementService {

  private final ScoreServiceFactory scoreServiceFactory;
  private final AdvertisementRepository advertisementRepository;
  private final AdvertisementConfigurationProperties configurationProperties;

  @Override
  public Mono<Void> create(Mono<AdvertisementRequestDto> advertisementRequestDtoMono) {
    return advertisementRequestDtoMono
        .map(this::from)
        .doOnNext(advertisement -> advertisementRepository.save(Mono.just(advertisement)).subscribe())
        .doOnSuccess(advertisement -> log.info("Advertisement {} created", advertisement.getUuid()))
        .then();
  }

  private Advertisement from(AdvertisementRequestDto advertisementRequestDto) {
    Advertisement advertisement = new Advertisement();
    advertisement.setDescription(advertisementRequestDto.getDescription());
    advertisement.setBuilding(new Building());
    advertisement.getBuilding().setTypology(advertisementRequestDto.getTypology());
    advertisement.getBuilding().setHouseSize(advertisementRequestDto.getHouseSize());
    advertisement.getBuilding().setGardenSize(advertisementRequestDto.getGardenSize());
    advertisement.getBuilding().setPictures(
        advertisementRequestDto.getPictures().stream()
            .map(pictureDto -> new Picture(pictureDto.getUrl(), pictureDto.getQuality()))
            .collect(Collectors.toList()));
    return advertisement;
  }

  @Override
  public Mono<Void> calculateScores() {
    return advertisementRepository.findAll()
        .filter(advertisement -> advertisement.getScore() == null)
        .map(this::calculateScore)
        .doOnNext(advertisement -> advertisementRepository.save(Mono.just(advertisement)).subscribe())
        .doOnNext(advertisement ->
            log.info("Advertisement {} updated with score {}", advertisement.getUuid(), advertisement.getScore()))
        .then();
  }

  private Advertisement calculateScore(Advertisement advertisement) {
    ScoreService scoreService = scoreServiceFactory.getStrategy(advertisement.getBuilding().getTypology());
    Integer score = scoreService.calculateScore(advertisement);
    advertisement.setScore(score);
    irrelevanceDetection(advertisement);
    return advertisement;
  }

  private void irrelevanceDetection(Advertisement advertisement) {
    if (advertisement.getScore() <= configurationProperties.getIrrelevantScore())
      advertisement.setIrrelevantSince(new Date());
  }

  @Override
  public Flux<PublicAdvertisementDto> findAllNoIrrelevant() {
    return advertisementRepository.findAll()
        .filter(advertisement -> advertisement.getIrrelevantSince() == null)
        .sort(Comparator.comparing(Advertisement::getScore).reversed())
        .map(PublicAdvertisementDto::of);
  }

  @Override
  public Flux<QualityAdvertisementDto> findAllIrrelevant() {
    return null;
  }
}
