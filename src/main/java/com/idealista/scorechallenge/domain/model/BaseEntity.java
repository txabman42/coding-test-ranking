package com.idealista.scorechallenge.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode
public class BaseEntity {

  private final UUID uuid;

  public BaseEntity() {
    this.uuid = UUID.randomUUID();
  }
}
