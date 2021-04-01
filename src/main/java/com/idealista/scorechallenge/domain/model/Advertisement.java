package com.idealista.scorechallenge.domain.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class Advertisement extends BaseEntity {

  private Typology typology;

  private String description;

  @Builder.Default
  private List<Picture> pictures = new ArrayList<>();

  private Integer houseSize;

  private Integer gardenSize;

  private Integer score;

  private Date irrelevantSince;

  public Advertisement() {
    super();
  }
}
