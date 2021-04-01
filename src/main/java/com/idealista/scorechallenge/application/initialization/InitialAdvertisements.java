package com.idealista.scorechallenge.application.initialization;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties
@PropertySource(value = "classpath:initial-advertisements.json", factory = JsonPropertySourceFactory.class)
public class InitialAdvertisements {

  private List<LinkedHashMap<String, String>> advertisements;
}
