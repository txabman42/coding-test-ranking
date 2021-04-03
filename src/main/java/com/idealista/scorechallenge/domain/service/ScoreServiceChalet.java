package com.idealista.scorechallenge.domain.service;

import com.idealista.scorechallenge.domain.configuration.KeyWords;
import com.idealista.scorechallenge.domain.model.Advertisement;
import com.idealista.scorechallenge.domain.model.Typology;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ScoreServiceChalet extends ScoreBaseService {

  private static final Integer DESCRIPTION_SCORE = 20;
  private static final Integer WORD_NUMBER = 50;

  public ScoreServiceChalet(KeyWords keyWords) {
    super(keyWords);
  }

  @Override
  protected Typology getScoreType() {
    return Typology.CHALET;
  }

  @Override
  protected Integer calculateWordNumberScore(Integer wordNumber) {
    return wordNumber > WORD_NUMBER ? DESCRIPTION_SCORE : 0;
  }


  @Override
  protected boolean isComplete(Advertisement advertisement) {
    return !advertisement.getDescription().isEmpty()
        && !advertisement.getBuilding().getPictures().isEmpty()
        && advertisement.getBuilding().getHouseSize() != null
        && advertisement.getBuilding().getGardenSize() != null;
  }
}
