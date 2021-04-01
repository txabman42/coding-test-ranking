package com.idealista.scorechallenge.infrastructure.inmemory.database;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class BaseDatabase<T, ID> {

  private final Map<ID, T> db = new HashMap<>();
}
