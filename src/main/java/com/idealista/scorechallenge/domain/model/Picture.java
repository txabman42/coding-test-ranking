package com.idealista.scorechallenge.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Picture extends BaseEntity {

  private String url;

  private Quality quality;

  public Picture() {
    super();
  }

  public Picture(String url, Quality quality) {
    super();
    this.url = url;
    this.quality = quality;
  }
}
