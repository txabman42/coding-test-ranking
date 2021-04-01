package com.idealista.scorechallenge.domain.service;

import com.idealista.scorechallenge.application.model.AdvertisementRequestDto;
import com.idealista.scorechallenge.domain.model.Advertisement;
import com.idealista.scorechallenge.domain.model.Picture;
import com.idealista.scorechallenge.domain.repository.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdvertisementServiceImpl implements AdvertisementService {

  private final AdvertisementRepository advertisementRepository;

  @Override
  public Mono<Void> create(Mono<AdvertisementRequestDto> advertisementRequestDtoMono) {
    return advertisementRequestDtoMono
        .map(this::from)
        .doOnNext(advertisement -> advertisementRepository.save(Mono.just(advertisement)))
        .doOnSuccess(advertisement -> log.info("Advertisement {} created", advertisement.getUuid()))
        .then();
  }

  private Advertisement from(AdvertisementRequestDto advertisementRequestDto) {
    Advertisement advertisement = new Advertisement();
    BeanUtils.copyProperties(advertisementRequestDto, advertisement, "pictures");
    advertisement.setPictures(advertisement.getPictures().stream()
        .map(pictureDto -> new Picture(pictureDto.getUrl(), pictureDto.getQuality()))
        .collect(Collectors.toList()));
    return advertisement;
  }
}
