package com.idealista.scorechallenge.application.rest.configuration;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.webflux.core.converters.WebFluxSupportConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
    ModelConverters.getInstance().addConverter(new WebFluxSupportConverter());
    return new OpenAPI()
        .info(new Info().title("Cabify Pool API").version(appVersion)
            .license(new License().name("Apache 2.0").url("http://springdoc.org")));
  }
}