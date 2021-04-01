package com.idealista.scorechallenge.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Picture extends BaseEntity {

  private String url;

  private String quality;

  public Picture() {
    super();
  }

  public Picture(String url, String quality) {
    super();
    this.url = url;
    this.quality = quality;
  }
}
