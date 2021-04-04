package com.idealista.scorechallenge.application.model;

import com.idealista.scorechallenge.domain.model.Typology;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AdvertisementRequestDto {

  private final Typology typology;

  private final String description;

  private final List<PictureDto> pictures;

  private final Integer houseSize;

  private final Integer gardenSize;

  public boolean isNotValid() {
    return typology == null || pictures == null;
  }
}
