package com.idealista.scorechallenge.application.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class AdvertisementRouter {

  private static final String ADVERTISEMENT_TAG = "Advertisement";
  private static final String ERROR_DEMO_RESPONSE =
      "{" +
          "\"timestamp\": \"2021-03-19T12:37:43.402+00:00\"," +
          "\"path\": \"/advertisements\"," +
          "\"status\": 400," +
          "\"error\": \"Bad Request\"," +
          "\"message\": null," +
          "\"requestId\": \"46e9fe19-6\"" +
          "}";

  @RouterOperations({
      @RouterOperation(
          path = "/v1/api/advertisements", beanClass = AdvertisementHandler.class, beanMethod = "calculateScores",
          operation = @Operation(
              tags = ADVERTISEMENT_TAG,
              operationId = "reset",
              responses = {
                  @ApiResponse(
                      description = "When the list is registered correctly.",
                      responseCode = "200"
                  ),
                  @ApiResponse(
                      description = "When there is a failure in the request format, expected headers, or the payload can't be unmarshalled.",
                      responseCode = "400",
                      content = @Content(
                          mediaType = MediaType.APPLICATION_JSON_VALUE,
                          examples = {@ExampleObject(value = ERROR_DEMO_RESPONSE)})
                  )
              }))})

  @Bean
  public RouterFunction<ServerResponse> monoAdvertisementRouterFunction(AdvertisementHandler advertisementHandler) {
    return RouterFunctions
        .route(
            RequestPredicates.PUT("/api/v1/advertisements").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
            advertisementHandler::calculateScores);
  }
}
