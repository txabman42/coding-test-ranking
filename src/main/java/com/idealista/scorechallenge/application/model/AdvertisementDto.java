package com.idealista.scorechallenge.application.model;

import com.idealista.scorechallenge.domain.model.Advertisement;
import com.idealista.scorechallenge.domain.model.Picture;
import com.idealista.scorechallenge.domain.model.Typology;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class AdvertisementDto {

  private final UUID id;

  private final Typology typology;

  private final String description;

  private final List<String> pictureUrls;

  private final Integer houseSize;

  private final Integer gardenSize;

  public static AdvertisementDto of(Advertisement advertisement) {
    return new AdvertisementDto(
        advertisement.getUuid(),
        advertisement.getBuilding().getTypology(),
        advertisement.getDescription(),
        advertisement.getBuilding().getPictures().stream().map(Picture::getUrl).collect(Collectors.toUnmodifiableList()),
        advertisement.getBuilding().getHouseSize(),
        advertisement.getBuilding().getGardenSize()
    );
  }
}
