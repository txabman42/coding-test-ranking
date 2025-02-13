package com.idealista.scorechallenge.application.rest

import com.idealista.scorechallenge.application.model.AdvertisementRequestDto
import com.idealista.scorechallenge.domain.model.Typology
import com.idealista.scorechallenge.domain.service.AdvertisementService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.ApplicationContext
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import spock.lang.Shared
import spock.lang.Specification

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf

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

    @WithMockUser
    def "route updateScores should response OK"() {
        given:
            advertisementService.calculateScores() >> Mono.empty()
        expect:
            webTestClient.mutateWith(csrf())
                    .post()
                    .uri("/api/private/v1/advertisements/scores")
                    .exchange()
                    .expectStatus().isOk()
    }

    @WithMockUser
    def "route getAll should response OK"() {
        given:
            advertisementService.findAllQuality() >> Flux.empty()
        expect:
            webTestClient.mutateWith(csrf())
                    .get()
                    .uri("/api/public/v1/advertisements")
                    .exchange()
                    .expectStatus().isOk()
    }

    @WithMockUser
    def "route getAllIrrelevant should response OK"() {
        given:
            advertisementService.findAllIrrelevant() >> Flux.empty()
        expect:
            webTestClient.mutateWith(csrf())
                    .get()
                    .uri("/api/private/v1/advertisements")
                    .exchange()
                    .expectStatus().isOk()
    }

    @WithMockUser
    def "given valid advertisementRequestDto route create should response OK"() {
        given:
            AdvertisementRequestDto advertisementRequestDto = new AdvertisementRequestDto(Typology.FLAT, null, [], null, null)
            advertisementService.create(_ as Mono<AdvertisementRequestDto>) >> Mono.empty()
        expect:
            webTestClient.mutateWith(csrf())
                    .post()
                    .uri("/api/private/v1/advertisements")
                    .bodyValue(advertisementRequestDto)
                    .exchange()
                    .expectStatus().isOk()
    }
}
