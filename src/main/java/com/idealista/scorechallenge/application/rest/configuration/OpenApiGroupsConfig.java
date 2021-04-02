package com.idealista.scorechallenge.application.rest.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiGroupsConfig {

  @Bean
  public GroupedOpenApi advertisementsOpenApi() {
    final String[] advertisementPaths = {"/api/v1/advertisements**"};
    return GroupedOpenApi.builder()
        .group("").pathsToMatch(advertisementPaths)
        .build();
  }
}