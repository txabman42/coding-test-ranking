package com.idealista.scorechallenge.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = false)
public class Advertisement extends BaseEntity {

  private String description;

  private Building building;

  private Integer score;

  private Date irrelevantSince;

  public Advertisement() {
    super();
  }
}
