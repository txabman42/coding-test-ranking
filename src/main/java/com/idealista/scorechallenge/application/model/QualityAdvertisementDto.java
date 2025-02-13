package com.idealista.scorechallenge.application.model;

import com.idealista.scorechallenge.domain.model.Advertisement;
import com.idealista.scorechallenge.domain.model.Picture;
import com.idealista.scorechallenge.domain.model.Typology;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Builder
@EqualsAndHashCode
public class QualityAdvertisementDto {

  private final UUID id;

  private final Typology typology;

  private final String description;

  private final List<String> pictureUrls;

  private final Integer houseSize;

  private final Integer gardenSize;

  private final Integer score;

  private final LocalDateTime irrelevantSince;

  public static QualityAdvertisementDto of(Advertisement advertisement) {
    return QualityAdvertisementDto.builder()
        .id(advertisement.getUuid())
        .typology(advertisement.getBuilding().getTypology())
        .description(advertisement.getDescription())
        .pictureUrls(advertisement.getBuilding().getPictures().stream().map(Picture::getUrl).collect(Collectors.toUnmodifiableList()))
        .houseSize(advertisement.getBuilding().getHouseSize())
        .gardenSize(advertisement.getBuilding().getGardenSize())
        .score(advertisement.getScore())
        .irrelevantSince(advertisement.getIrrelevantSince())
        .build();
  }
}
