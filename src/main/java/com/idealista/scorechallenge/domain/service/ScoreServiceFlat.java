package com.idealista.scorechallenge.domain.service;

import com.idealista.scorechallenge.domain.configuration.KeyWords;
import com.idealista.scorechallenge.domain.model.Advertisement;
import com.idealista.scorechallenge.domain.model.Typology;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ScoreServiceFlat extends ScoreBaseService {

  private static final Integer LOW_DESCRIPTION_SCORE = 10;
  private static final Integer HIGH_DESCRIPTION_SCORE = 20;
  private static final Integer LOW_WORD_NUMBER = 30;
  private static final Integer HIGH_WORD_NUMBER = 50;

  public ScoreServiceFlat(KeyWords keyWords) {
    super(keyWords);
  }

  @Override
  protected Typology getScoreType() {
    return Typology.FLAT;
  }

  @Override
  protected Integer calculateWordNumberScore(Integer wordNumber) {
    if (wordNumber >= HIGH_WORD_NUMBER)
      return HIGH_DESCRIPTION_SCORE;
    else if (wordNumber >= LOW_WORD_NUMBER)
      return LOW_DESCRIPTION_SCORE;
    else
      return 0;
  }

  @Override
  protected boolean isComplete(Advertisement advertisement) {
    return !advertisement.getDescription().isEmpty()
        && !advertisement.getBuilding().getPictures().isEmpty()
        && advertisement.getBuilding().getHouseSize() != null;
  }
}
