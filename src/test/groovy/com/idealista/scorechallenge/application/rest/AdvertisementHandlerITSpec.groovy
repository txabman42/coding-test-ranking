package com.idealista.scorechallenge.application.rest

import com.idealista.scorechallenge.domain.service.AdvertisementService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import spock.lang.Shared
import spock.lang.Specification

@WebFluxTest
@ContextConfiguration(classes = [AdvertisementRouter, AdvertisementHandler])
class AdvertisementHandlerITSpec extends Specification {

    @Autowired
    private ApplicationContext applicationContext

    @SpringBean
    private AdvertisementService advertisementService = Mock(AdvertisementService)

    @Shared
    WebTestClient webTestClient

    def setup() {
        webTestClient = WebTestClient.bindToApplicationContext(applicationContext).build()
    }

    def "route updateScores should response OK"() {
        given:
            advertisementService.calculateScores() >> Mono.empty()
        expect:
            webTestClient.post()
                .uri("/api/private/v1/advertisements/scores")
                .exchange()
                .expectStatus().isOk()
    }

    def "route getAll should response OK"() {
        given:
            advertisementService.findAllNoIrrelevant() >> Flux.empty()
        expect:
            webTestClient.get()
                    .uri("/api/public/v1/advertisements")
                    .exchange()
                    .expectStatus().isOk()
    }
}
