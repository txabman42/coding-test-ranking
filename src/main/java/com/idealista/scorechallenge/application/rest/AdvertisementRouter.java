package com.idealista.scorechallenge.application.rest;

import com.idealista.scorechallenge.application.model.AdvertisementRequestDto;
import com.idealista.scorechallenge.application.model.PublicAdvertisementDto;
import com.idealista.scorechallenge.application.model.QualityAdvertisementDto;
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
import org.springframework.web.bind.annotation.RequestMethod;
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
          path = "/api/private/v1/advertisements/scores", beanClass = AdvertisementHandler.class, beanMethod = "calculateScores",
          method = RequestMethod.POST,
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
              })),
      @RouterOperation(
          path = "/api/public/v1/advertisements", beanClass = AdvertisementHandler.class, beanMethod = "getAll",
          method = RequestMethod.GET,
          operation = @Operation(
              tags = ADVERTISEMENT_TAG,
              operationId = "getAll",
              responses = {
                  @ApiResponse(
                      description = "All no irrelevant advertisements ordered by score",
                      responseCode = "200",
                      content = @Content(
                          mediaType = MediaType.APPLICATION_JSON_VALUE,
                          schema = @Schema(implementation = QualityAdvertisementDto.class),
                          examples = {@ExampleObject(value = """
                              [
                                {
                                  "uuid": "00000000-0000-0000-0000-000000000000",
                                  "typology": "FLAT",
                                  "description": "Pisazo",
                                  "pictureUrls": [
                                    "http://www.idealista.com/pictures/1"
                                  ],
                                  "houseSize": "100",
                                  "gardenSize": null
                                }
                              ]
                              """)})),
                  @ApiResponse(
                      description = "When there is a failure in the request format, expected headers, or the payload can't be unmarshalled.",
                      responseCode = "400",
                      content = @Content(
                          mediaType = MediaType.APPLICATION_JSON_VALUE,
                          examples = {@ExampleObject(value = ERROR_DEMO_RESPONSE)}))})),
      @RouterOperation(
          path = "/api/private/v1/advertisements", beanClass = AdvertisementHandler.class, beanMethod = "getAllIrrelevant",
          method = RequestMethod.GET,
          operation = @Operation(
              tags = ADVERTISEMENT_TAG,
              operationId = "getAllIrrelevant",
              responses = {
                  @ApiResponse(
                      description = "All irrelevant advertisements",
                      responseCode = "200",
                      content = @Content(
                          mediaType = MediaType.APPLICATION_JSON_VALUE,
                          schema = @Schema(implementation = PublicAdvertisementDto.class),
                          examples = {@ExampleObject(value = """
                              [
                                {
                                  "uuid": "00000000-0000-0000-0000-000000000000",
                                  "typology": "FLAT",
                                  "description": "Pisazo",
                                  "pictureUrls": [
                                    "http://www.idealista.com/pictures/1"
                                  ],
                                  "houseSize": "100",
                                  "gardenSize": null,
                                  "score": 100,
                                  "irrelevantSince": null
                                }
                              ]
                              """)})),
                  @ApiResponse(
                      description = "When there is a failure in the request format, expected headers, or the payload can't be unmarshalled.",
                      responseCode = "400",
                      content = @Content(
                          mediaType = MediaType.APPLICATION_JSON_VALUE,
                          examples = {@ExampleObject(value = ERROR_DEMO_RESPONSE)}))})),
      @RouterOperation(
          path = "/api/private/v1/advertisements", beanClass = AdvertisementHandler.class, beanMethod = "create",
          method = RequestMethod.POST,
          operation = @Operation(
              tags = ADVERTISEMENT_TAG,
              operationId = "create",
              requestBody = @RequestBody(required = true, description = "Create a advertisement",
                  content = @Content(
                      mediaType = MediaType.APPLICATION_JSON_VALUE,
                      schema = @Schema(implementation = AdvertisementRequestDto.class),
                      examples = {@ExampleObject(value = """
                             {
                                "typology": "FLAT",
                                "description": "Pisazo",
                                "pictures": [
                                  {
                                    "url": "http://www.idealista.com/pictures/4",
                                    "quality": "HD"
                                  }
                                ],
                                "houseSize": "100",
                                "gardenSize": null,
                             } 
                          """)})),
              responses = {
                  @ApiResponse(
                      description = "Advertisement created correctly",
                      responseCode = "202"),
                  @ApiResponse(
                      description = "When there is a failure in the request format, expected headers, or the payload can't be unmarshalled.",
                      responseCode = "400",
                      content = @Content(
                          mediaType = MediaType.APPLICATION_JSON_VALUE,
                          examples = {@ExampleObject(value = ERROR_DEMO_RESPONSE)}))}))
  })

  @Bean
  public RouterFunction<ServerResponse> monoAdvertisementRouterFunction(AdvertisementHandler advertisementHandler) {
    return RouterFunctions
        .route(
            RequestPredicates.POST("/api/private/v1/advertisements/scores").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
            advertisementHandler::calculateScores)
        .andRoute(
            RequestPredicates.GET("/api/public/v1/advertisements").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
            advertisementHandler::getAll)
        .andRoute(
            RequestPredicates.GET("/api/private/v1/advertisements").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
            advertisementHandler::getAllIrrelevant)
        .andRoute(
            RequestPredicates.POST("/api/private/v1/advertisements").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
            advertisementHandler::create);
  }
}
