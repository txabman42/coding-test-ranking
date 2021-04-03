package com.idealista.scorechallenge.domain.service;

import com.idealista.scorechallenge.domain.configuration.KeyWords;
import com.idealista.scorechallenge.domain.model.Advertisement;
import com.idealista.scorechallenge.domain.model.Picture;
import com.idealista.scorechallenge.domain.model.Quality;
import com.idealista.scorechallenge.domain.model.Typology;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

@Slf4j
@RequiredArgsConstructor
public abstract class ScoreBaseService implements ScoreService {

  private static final Integer NO_PICTURE_SCORE = -10;
  private static final Integer PICTURE_NO_HD_SCORE = 10;
  private static final Integer PICTURE_HD_SCORE = 20;
  private static final Integer DESCRIPTION_SCORE = 5;
  private static final Integer KEY_WORD_SCORE  = 5;
  private static final Integer COMPLETE_SCORE = 40;

  private final KeyWords keyWords;

  @Override
  public Typology getType() {
    return getScoreType();
  }

  protected abstract Typology getScoreType();

  @Override
  public Integer calculateScore(Advertisement advertisement) {
    return calculateScorePhotos(advertisement.getBuilding().getPictures())
        + calculateScoreDescription(advertisement.getDescription())
        + calculateScoreComplete(advertisement);
  }

  private Integer calculateScorePhotos(List<Picture> pictures) {
    return pictures.isEmpty()
        ? NO_PICTURE_SCORE
        : pictures.stream().map(this::mapPictureToScore).reduce(0, Integer::sum);
  }

  private Integer mapPictureToScore(Picture picture) {
    return picture.getQuality().equals(Quality.HD) ? PICTURE_HD_SCORE : PICTURE_NO_HD_SCORE;
  }

  private Integer calculateScoreDescription(String description) {
    int score = 0;
    if (!description.isBlank())
      score += DESCRIPTION_SCORE;
    StringTokenizer descriptionTokenizer = new StringTokenizer(description);
    score += calculateWordNumberScore(descriptionTokenizer.countTokens()) + calculateKeyWordsScore(descriptionTokenizer);
    return score;
  }

  protected abstract Integer calculateWordNumberScore(Integer wordNumber);

  private Integer calculateKeyWordsScore(StringTokenizer tokenizer) {
    long descriptionKeyWordNumber = Collections.list(tokenizer).stream()
        .filter(token -> keyWords.getWords().contains((String) token))
        .count();
    return Math.toIntExact(descriptionKeyWordNumber) * KEY_WORD_SCORE;
  }

  private Integer calculateScoreComplete(Advertisement advertisement) {
    return isComplete(advertisement) ? COMPLETE_SCORE : 0;
  }

  protected abstract boolean isComplete(Advertisement advertisement);
}
