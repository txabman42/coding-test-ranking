package com.idealista.scorechallenge.application.rest

import com.idealista.scorechallenge.application.model.AdvertisementRequestDto
import com.idealista.scorechallenge.application.model.PublicAdvertisementDto
import com.idealista.scorechallenge.application.model.QualityAdvertisementDto
import com.idealista.scorechallenge.domain.model.Typology
import com.idealista.scorechallenge.domain.service.AdvertisementService
import org.springframework.http.HttpStatus
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Shared
import spock.lang.Specification

class AdvertisementHandlerSpec extends Specification {

    AdvertisementService advertisementService = Mock(AdvertisementService)
    AdvertisementHandler advertisementHandler = new AdvertisementHandler(advertisementService)

    @Shared
    ServerRequest serverRequest

    def setup() {
        serverRequest = Mock(ServerRequest)
    }

    def "calculateScores should return OK response"() {
        given:
            1 * advertisementService.calculateScores() >> Mono.empty()
        when:
            def result = advertisementHandler.calculateScores(serverRequest)
        then:
            StepVerifier.create(result).expectNextMatches({
                response -> response.statusCode() == HttpStatus.OK
            }).verifyComplete()
    }

    def "getAll should return all no irrelevant advertisements"() {
        given:
            UUID uuid = UUID.randomUUID()
            PublicAdvertisementDto advertisementDto = PublicAdvertisementDto.builder().id(uuid).build()
            1 * advertisementService.findAllNoIrrelevant() >> Flux.fromIterable([advertisementDto])
        when:
            def result = advertisementHandler.getAll(serverRequest)
        then:
            StepVerifier.create(result).expectNextMatches({
                response -> response.statusCode() == HttpStatus.OK
            }).verifyComplete()
    }

    def "getAllIrrelevant should return all irrelevant advertisements"() {
        given:
            UUID uuid = UUID.randomUUID()
            QualityAdvertisementDto advertisementDto = QualityAdvertisementDto.builder().id(uuid).build()
            1 * advertisementService.findAllNoIrrelevant() >> Flux.fromIterable([advertisementDto])
        when:
            def result = advertisementHandler.getAll(serverRequest)
        then:
            StepVerifier.create(result).expectNextMatches({
                response -> response.statusCode() == HttpStatus.OK
            }).verifyComplete()
    }

    @WithMockUser
    def "given valid advertisementRequestDto create should response OK"() {
        given:
            AdvertisementRequestDto advertisementRequestDto = new AdvertisementRequestDto(Typology.FLAT, null, [], null, null)
            1 * serverRequest.bodyToMono(AdvertisementRequestDto) >> Mono.just(advertisementRequestDto)
            1 * advertisementService.create(_ as Mono<AdvertisementRequestDto>) >> Mono.empty()
        when:
            def result = advertisementHandler.create(serverRequest)
        then:
            StepVerifier.create(result).expectNextMatches({
                response -> response.statusCode() == HttpStatus.OK
            }).verifyComplete()
    }

    @WithMockUser
    def "given invalid advertisementRequestDto create should response BAD_REQUEST"() {
        given:
            AdvertisementRequestDto advertisementRequestDto = new AdvertisementRequestDto(null, null, [], null, null)
            1 * serverRequest.bodyToMono(AdvertisementRequestDto) >> Mono.just(advertisementRequestDto)
        when:
            def result = advertisementHandler.create(serverRequest)
        then:
            StepVerifier.create(result)
                    .expectErrorMatches(throwable -> throwable instanceof ResponseStatusException).verify()
    }
}
