package com.idealista.scorechallenge.application.rest.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("idealista.security")
public class SecurityConfigurationProperties {

  private String username;

  private String password;

  private String role;
}
