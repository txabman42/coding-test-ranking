package com.idealista.scorechallenge.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;


@Data
@EqualsAndHashCode(callSuper = false)
public class Advertisement extends BaseEntity {

  private String description;

  private Building building;

  private Integer score;

  private LocalDateTime irrelevantSince;

  public Advertisement() {
    super();
  }
}
