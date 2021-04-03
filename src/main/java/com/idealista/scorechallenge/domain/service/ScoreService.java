package com.idealista.scorechallenge.domain.service;

import com.idealista.scorechallenge.domain.model.Advertisement;
import com.idealista.scorechallenge.domain.model.Typology;

/**
 * Defines the functionality for score calculations
 */
public interface ScoreService {

  /**
   * Get score service type
   *
   * @return Type of the service
   */
  Typology getType();

  /**
   * Calculates the score of an {@link Advertisement}
   *
   * @param advertisement Entity to be used for the calculation
   * @return Computed score
   */
  Integer calculateScore(Advertisement advertisement);
}
