package com.idealista.scorechallenge.domain.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("idealista.advertisement")
public class AdvertisementConfigurationProperties {

  private Integer irrelevantScore;
}
