package com.idealista.scorechallenge.application.model;

import com.idealista.scorechallenge.domain.model.Quality;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PictureDto {

  private final String url;

  private final Quality quality;

}
