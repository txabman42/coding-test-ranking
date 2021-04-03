package com.idealista.scorechallenge.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Building {

  private Typology typology;

  private List<Picture> pictures = new ArrayList<>();

  private Integer houseSize;

  private Integer gardenSize;
}
