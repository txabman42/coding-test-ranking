package com.idealista.scorechallenge.application.rest

import com.idealista.scorechallenge.application.model.PublicAdvertisementDto
import com.idealista.scorechallenge.application.model.QualityAdvertisementDto
import com.idealista.scorechallenge.domain.service.AdvertisementService
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Specification

class AdvertisementHandlerSpec extends Specification {

    AdvertisementService advertisementService = Mock(AdvertisementService)
    AdvertisementHandler advertisementHandler = new AdvertisementHandler(advertisementService)

    def "calculateScores should return OK response"() {
        given:
            ServerRequest serverRequest = Mock(ServerRequest)
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
            ServerRequest serverRequest = Mock(ServerRequest)
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
            ServerRequest serverRequest = Mock(ServerRequest)
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
}
