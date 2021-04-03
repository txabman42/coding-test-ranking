package com.idealista.scorechallenge.domain.service;

import com.idealista.scorechallenge.domain.configuration.KeyWords;
import com.idealista.scorechallenge.domain.model.Advertisement;
import com.idealista.scorechallenge.domain.model.Typology;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ScoreServiceGarage extends ScoreBaseService {

  public ScoreServiceGarage(KeyWords keyWords) {
    super(keyWords);
  }

  @Override
  protected Typology getScoreType() {
    return Typology.GARAGE;
  }

  @Override
  protected Integer calculateWordNumberScore(Integer wordNumber) {
    return 0;
  }

  @Override
  protected boolean isComplete(Advertisement advertisement) {
    return !advertisement.getBuilding().getPictures().isEmpty();
  }
}
