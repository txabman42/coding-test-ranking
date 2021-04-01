package com.idealista.scorechallenge.application.model;

import com.idealista.scorechallenge.domain.model.Typology;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class AdvertisementDto {

  private final UUID id;

  private final Typology typology;

  private final String description;

  private final List<String> pictureUrls;

  private final Integer houseSize;

  private final Integer gardenSize;

}
