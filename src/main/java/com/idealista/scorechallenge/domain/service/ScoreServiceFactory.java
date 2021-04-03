package com.idealista.scorechallenge.domain.service;

import com.idealista.scorechallenge.domain.model.Typology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ScoreServiceFactory {

  private static final Map<Typology, ScoreService> scoreServiceList = new HashMap<>();

  @Autowired
  public ScoreServiceFactory(List<ScoreService> scoreServices) {
    for (ScoreService service : scoreServices) {
      scoreServiceList.put(service.getType(), service);
    }
  }

  public ScoreService getStrategy(Typology typology) {
    ScoreService service = scoreServiceList.get(typology);
    if (service == null) {
      throw new IllegalArgumentException("Unknown score service for: " + typology);
    }
    return service;
  }
}
