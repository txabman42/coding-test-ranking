package com.idealista.scorechallenge.domain.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
public class KeyWords {

  @Value("classpath:key-words.txt")
  private List<String> words;
}
